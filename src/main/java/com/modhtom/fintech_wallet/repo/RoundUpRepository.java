package com.modhtom.fintech_wallet.repo;

import com.modhtom.fintech_wallet.model.RoundUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@Repository
public interface RoundUpRepository extends JpaRepository<RoundUp, UUID> {
    List<RoundUp> findByAllocatedFalse();
    Page<RoundUp> findByUser_Username(String username, Pageable pageable);
}
