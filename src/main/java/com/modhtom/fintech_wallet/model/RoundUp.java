package com.modhtom.fintech_wallet.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "roundups")
public class RoundUp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction  transaction ;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User  user;
    private BigDecimal spare_amount;
    private String currency;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private boolean allocated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private RoundUpBatches batch;
    public boolean getAllocated() {
        return allocated;
    }
}
