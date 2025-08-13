package com.modhtom.fintech_wallet.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundUpResponseDTO {
        @Schema(description = "The amount of spare change saved from a transaction.", example = "0.66")
        private BigDecimal spare_amount;
        @Schema(description = "The currency of the round-up amount.", example = "USD")
        private String currency;
        @Schema(description = "Timestamp of when the round-up was created.")
        private Date created_at;
        @Schema(description = "Indicates if this round-up has been allocated to the portfolio.", example = "false")
        private boolean allocated;
}
