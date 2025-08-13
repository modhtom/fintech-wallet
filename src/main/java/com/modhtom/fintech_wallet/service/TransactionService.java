package com.modhtom.fintech_wallet.service;

import com.modhtom.fintech_wallet.DTO.TransactionRequestDTO;
import com.modhtom.fintech_wallet.DTO.TransactionResponseDTO;
import com.modhtom.fintech_wallet.config.TransactionSpecification;
import com.modhtom.fintech_wallet.model.Account;
import com.modhtom.fintech_wallet.model.Transaction;
import com.modhtom.fintech_wallet.model.User;
import com.modhtom.fintech_wallet.model.UserPrincipal;
import com.modhtom.fintech_wallet.repo.AccountRepository;
import com.modhtom.fintech_wallet.repo.TransactionRepository;
import com.modhtom.fintech_wallet.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Date;

@Service
public class TransactionService {
    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private TransactionRepository TranRepo;
    @Autowired
    private AccountRepository accRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO, UserPrincipal user) {
        User currentUser = UserRepo.findUserByUsername(user.getUsername());
        if(currentUser==null){
            return null;
        }

        Account account = accRepo.findById(requestDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + requestDTO.getAccountId()));

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("User does not have permission to access this account.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAccount(account);
        newTransaction.setAmount(requestDTO.getAmount());
        newTransaction.setCurrency(requestDTO.getCurrency());
        newTransaction.setDescription(requestDTO.getDescription());
        newTransaction.setMerchant(requestDTO.getMerchant());
        newTransaction.setType(requestDTO.getType());
        newTransaction.setHappened_at(new Date());

        Transaction savedTransaction = TranRepo.save(newTransaction);
        eventPublisher.publishEvent(savedTransaction);

        return maptoTransactionResponseDTO(savedTransaction);
    }


    public Page<TransactionResponseDTO> getTransactionsForUser(String username, String merchant, Pageable pageable) {
        Specification<Transaction> spec = Specification.where(TransactionSpecification.forUser(username));

        if (merchant != null && !merchant.isEmpty()) {
            spec = spec.and(TransactionSpecification.hasMerchant(merchant));
        }

        return TranRepo.findAll(spec, pageable).map(this::maptoTransactionResponseDTO);
    }

    private TransactionResponseDTO maptoTransactionResponseDTO(Transaction savedTransaction){
        TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
        transactionResponseDTO.setId(savedTransaction.getId());
        transactionResponseDTO.setAccount(savedTransaction.getAccount());
        transactionResponseDTO.setAmount(savedTransaction.getAmount());
        transactionResponseDTO.setCurrency(savedTransaction.getCurrency());
        transactionResponseDTO.setDescription(savedTransaction.getDescription());
        transactionResponseDTO.setMerchant(savedTransaction.getMerchant());
        transactionResponseDTO.setType(savedTransaction.getType());
        transactionResponseDTO.setHappened_at(savedTransaction.getHappened_at());
        transactionResponseDTO.setCreated_at(savedTransaction.getCreated_at());

        return transactionResponseDTO;
    }
}
