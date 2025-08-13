package com.modhtom.fintech_wallet.service;

import com.modhtom.fintech_wallet.DTO.PortfolioResponseDTO;
import com.modhtom.fintech_wallet.DTO.RoundUpResponseDTO;
import com.modhtom.fintech_wallet.model.*;
import com.modhtom.fintech_wallet.repo.PortfolioRepository;
import com.modhtom.fintech_wallet.repo.RoundUpBatchesRepository;
import com.modhtom.fintech_wallet.repo.RoundUpRepository;
import com.modhtom.fintech_wallet.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    @Autowired
    private RoundUpRepository roundUpRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private RoundUpBatchesRepository batchRepository;
    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 1 * * SUN")
    @Transactional
    public void processWeeklyRoundUps() {
        List<RoundUp> unallocatedRoundUps = roundUpRepository.findByAllocatedFalse();

        Map<User, List<RoundUp>> roundUpsByUser = unallocatedRoundUps.stream()
                .collect(Collectors.groupingBy(RoundUp::getUser));

        RoundUpBatches batch = new RoundUpBatches();
        batch.setBatch_date(LocalDate.now().atStartOfDay());

        batchRepository.save(batch);

        for (Map.Entry<User, List<RoundUp>> entry : roundUpsByUser.entrySet()) {
            User user = entry.getKey();
            List<RoundUp> userRoundUps = entry.getValue();

            BigDecimal weeklySum = userRoundUps.stream()
                    .map(RoundUp::getSpare_amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Portfolio portfolio = portfolioRepository.findByUser(user);

            if(portfolio == null) {
                portfolio = new Portfolio(user);
            }

            BigDecimal valueAfterDeposit = portfolio.getCurrentValue().add(weeklySum);
            BigDecimal interest = valueAfterDeposit.multiply(new BigDecimal("0.01"));
            BigDecimal finalValue = valueAfterDeposit.add(interest);

            portfolio.setCurrentValue(finalValue);
            portfolioRepository.save(portfolio);

            userRoundUps.forEach(roundUp -> {
                roundUp.setAllocated(true);
                roundUp.setBatch(batch);
            });
            roundUpRepository.saveAll(userRoundUps);
        }

    }

    @Transactional
    public void processRoundUpsNow() {
        List<RoundUp> unallocatedRoundUps = roundUpRepository.findByAllocatedFalse();

        Map<User, List<RoundUp>> roundUpsByUser = unallocatedRoundUps.stream()
                .collect(Collectors.groupingBy(RoundUp::getUser));

        RoundUpBatches batch = new RoundUpBatches();
        batch.setBatch_date(LocalDate.now().atStartOfDay());

        batchRepository.save(batch);

        for (Map.Entry<User, List<RoundUp>> entry : roundUpsByUser.entrySet()) {
            User user = entry.getKey();
            List<RoundUp> userRoundUps = entry.getValue();

            BigDecimal weeklySum = userRoundUps.stream()
                    .map(RoundUp::getSpare_amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Portfolio portfolio = portfolioRepository.findByUser(user);

            if(portfolio == null) {
                portfolio = new Portfolio(user);
            }


            BigDecimal valueAfterDeposit = portfolio.getCurrentValue().add(weeklySum);
            BigDecimal interest = valueAfterDeposit.multiply(new BigDecimal("0.01"));
            BigDecimal finalValue = valueAfterDeposit.add(interest);

            portfolio.setCurrentValue(finalValue);
            portfolioRepository.save(portfolio);

            userRoundUps.forEach(roundUp -> {
                roundUp.setAllocated(true);
                roundUp.setBatch(batch);
            });
            roundUpRepository.saveAll(userRoundUps);
        }

    }


    public PortfolioResponseDTO getProfile(UserPrincipal userDetails) {
        User user = userRepository.findUserByUsername(userDetails.getUsername());
        Portfolio portfolio = portfolioRepository.findByUser(user);
        if (portfolio == null) {
            portfolio = new Portfolio();
        }
        return mapToPortfolioResponseDTO(portfolio);
    }
    private PortfolioResponseDTO mapToPortfolioResponseDTO(Portfolio portfolio){
        PortfolioResponseDTO responseDTO = new PortfolioResponseDTO();
        responseDTO.setBatch_id(portfolio.getId());
        responseDTO.setCurrentValue(portfolio.getCurrentValue());
        responseDTO.setUpdatedAt(portfolio.getUpdatedAt());
        responseDTO.setCreated_at(portfolio.getCreatedAt());
        return responseDTO;
    }

}
