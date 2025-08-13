package com.modhtom.fintech_wallet.repo;

import com.modhtom.fintech_wallet.model.Portfolio;
import com.modhtom.fintech_wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository  extends JpaRepository<Portfolio, UUID> {
    Portfolio findByUser(User user);
}
