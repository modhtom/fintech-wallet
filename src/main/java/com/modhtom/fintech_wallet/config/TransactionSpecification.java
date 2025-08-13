package com.modhtom.fintech_wallet.config;

import com.modhtom.fintech_wallet.model.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
    public static Specification<Transaction> hasMerchant(String merchant) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("merchant"), merchant);
    }

    public static Specification<Transaction> forUser(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("account").get("user").get("username"), username);
    }
}