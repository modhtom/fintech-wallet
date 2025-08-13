package com.modhtom.fintech_wallet.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequestDTO {
    @Schema(description = "The currency of the account.", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String currency;
    @Schema(description = "The initial balance of the account.", example = "1000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private BigDecimal balance;
}
