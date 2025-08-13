package com.modhtom.fintech_wallet.service;

import com.modhtom.fintech_wallet.DTO.RoundUpResponseDTO;
import com.modhtom.fintech_wallet.model.RoundUp;
import com.modhtom.fintech_wallet.model.Transaction;
import com.modhtom.fintech_wallet.repo.RoundUpRepository;
import com.modhtom.fintech_wallet.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RoundUpService {
    @Autowired
    private RoundUpRepository repo;
    @Autowired
    private UserRepository UserRepo;

    @EventListener
    @Transactional
    public void Calculation(Transaction transaction) {
        BigDecimal ceiling = transaction.getAmount().setScale(0, RoundingMode.CEILING);
        BigDecimal spareAmount = ceiling.subtract(transaction.getAmount());
        if (spareAmount.compareTo(BigDecimal.ZERO) > 0) {
            RoundUp roundUp = new RoundUp();
            roundUp.setTransaction(transaction);
            roundUp.setUser(transaction.getAccount().getUser());
            roundUp.setSpare_amount(spareAmount);
            roundUp.setCurrency(transaction.getCurrency());

            roundUp.setAllocated(false);
            roundUp.setBatch(null);


            repo.save(roundUp);
        }
    }
    public Page<RoundUpResponseDTO> getRoundUpsForUser(String username, Pageable pageable) {
        Page<RoundUp> roundUps = repo.findByUser_Username(username, pageable);
        return roundUps.map(this::mapToResponseDTO);
    }

    private RoundUpResponseDTO mapToResponseDTO(RoundUp roundUp) {
        RoundUpResponseDTO responseDTO = new RoundUpResponseDTO();
        responseDTO.setSpare_amount(roundUp.getSpare_amount());
        responseDTO.setCurrency(roundUp.getCurrency());
        responseDTO.setAllocated(roundUp.getAllocated());
        return responseDTO;
    }
}
