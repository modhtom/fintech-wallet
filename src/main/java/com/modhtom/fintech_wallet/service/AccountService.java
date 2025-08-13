package com.modhtom.fintech_wallet.service;

import com.modhtom.fintech_wallet.DTO.AccountRequestDTO;
import com.modhtom.fintech_wallet.DTO.AccountResponseDTO;
import com.modhtom.fintech_wallet.model.Account;
import com.modhtom.fintech_wallet.model.User;
import com.modhtom.fintech_wallet.repo.AccountRepository;
import com.modhtom.fintech_wallet.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accRepo;
    @Autowired
    private UserRepository userRepo;

    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO, String username) {
        User owner = userRepo.findUserByUsername(username);
        if(owner==null){
            return null;
        }
        Account newAccount = new Account();
        newAccount.setCurrency(requestDTO.getCurrency());
        newAccount.setBalance(requestDTO.getBalance());
        newAccount.setAccount_number(UUID.randomUUID().toString());

        owner.addAccount(newAccount);

        Account savedAccount = accRepo.save(newAccount);

        return mapToAccountResponseDTO(savedAccount);
    }

    private AccountResponseDTO mapToAccountResponseDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccount_number(account.getAccount_number());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setCreated_at(account.getCreated_at());
        return dto;
    }
}
