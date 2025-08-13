package com.modhtom.fintech_wallet.repo;

import com.modhtom.fintech_wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByUsername(String username);
}
