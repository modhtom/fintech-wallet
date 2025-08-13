package com.modhtom.fintech_wallet.repo;

import com.modhtom.fintech_wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;
@Repository

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {

}
