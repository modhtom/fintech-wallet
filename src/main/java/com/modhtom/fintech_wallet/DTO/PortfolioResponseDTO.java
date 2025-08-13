package com.modhtom.fintech_wallet.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PortfolioResponseDTO {
    @Schema(description = "Unique identifier for the portfolio.")
    private UUID batch_id;
    @Schema(description = "The total current value of the portfolio, including savings and interest.", example = "152.78")
    private BigDecimal currentValue;
    @Schema(description = "Timestamp of when the portfolio was created.")
    private LocalDateTime created_at;
    @Schema(description = "Timestamp of the last update to the portfolio's value.")
    private LocalDateTime updatedAt;
}
