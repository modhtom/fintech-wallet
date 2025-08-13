package com.modhtom.fintech_wallet.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequestDTO {
    @Schema(description = "The ID of the account for this transaction.", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID accountId;
    @Schema(description = "The transaction amount.", example = "15.34", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private BigDecimal amount;
    @Schema(description = "Credit Card used currency", example = "USD")
    private String currency;
    @Schema(description = "The merchant where the transaction occurred.", example = "929")
    private String merchant;
    @Schema(description = "A description of the transaction.", example = "Morning Coffee")
    private String description;
    @Schema(description = "Credit Card used Type", example = "Debit")
    private String type;
}
