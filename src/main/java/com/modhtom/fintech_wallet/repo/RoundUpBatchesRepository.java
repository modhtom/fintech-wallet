package com.modhtom.fintech_wallet.repo;

import com.modhtom.fintech_wallet.model.RoundUpBatches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoundUpBatchesRepository extends JpaRepository<RoundUpBatches, UUID> {
}
