package com.modhtom.fintech_wallet.controller;

import com.modhtom.fintech_wallet.DTO.PortfolioResponseDTO;
import com.modhtom.fintech_wallet.model.UserPrincipal;
import com.modhtom.fintech_wallet.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
@Tag(name = "Portfolio Management", description = "Endpoints for viewing portfolio status and triggering admin actions.")
@SecurityRequirement(name = "Bearer Authentication")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @Operation(
            summary = "Get user's portfolio",
            description = "Retrieves the current state of the authenticated user's simulated investment portfolio."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved portfolio")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @GetMapping
    public ResponseEntity<PortfolioResponseDTO> getPortfolioForUser(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        PortfolioResponseDTO responseDTO = portfolioService.getProfile(userDetails);
        if(responseDTO==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Trigger weekly processing (Admin only)",
            description = "Manually triggers the weekly job to process all unallocated round-ups. Requires ADMIN role."
    )
    @ApiResponse(responseCode = "200", description = "Job triggered successfully")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @ApiResponse(responseCode = "403", description = "User is not an admin")
    @PostMapping("/process-now")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createRoundUp() {
        portfolioService.processRoundUpsNow();

        return ResponseEntity.ok("Round-up processing job triggered successfully!");
    }
}
