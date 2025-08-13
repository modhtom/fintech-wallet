package com.modhtom.fintech_wallet.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountResponseDTO {
    @Schema(description = "The unique account number.", example = "ACC-a1b2c3d4e5f6")
    private String account_number;

    @Schema(description = "The currency of the account.", example = "USD")
    private String currency;

    @Schema(description = "The current balance of the account.", example = "1000.00")
    private BigDecimal balance;

    @Schema(description = "Timestamp of when the account was created.")
    private Date created_at;
}
