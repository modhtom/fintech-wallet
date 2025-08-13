package com.modhtom.fintech_wallet.DTO;

import com.modhtom.fintech_wallet.model.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponseDTO {
    @Schema(description = "Unique identifier for the transaction.")
    private UUID id;
    @Schema(description = "Account that the transaction happened in.")
    private Account account;
    @Schema(description = "The amount of the transaction.")
    private BigDecimal amount;
    @Schema(description = "Credit Card used currency", example = "USD")
    private String currency;
    @Schema(description = "The merchant where the transaction occurred.")
    private String merchant;
    @Schema(description = "A description of the transaction.")
    private String description;
    @Schema(description = "Timestamp of when the transaction was happened.")
    private Date happened_at;
    @Schema(description = "Credit Card used Type", example = "Debit")
    private String type;
    @Schema(description = "Timestamp of when the transaction was created.")
    private Date created_at;
}
