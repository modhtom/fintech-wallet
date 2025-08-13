package com.modhtom.fintech_wallet.repo;

import com.modhtom.fintech_wallet.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
