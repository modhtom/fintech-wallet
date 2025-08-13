package com.modhtom.fintech_wallet.controller;

import com.modhtom.fintech_wallet.DTO.RoundUpResponseDTO;
import com.modhtom.fintech_wallet.model.UserPrincipal;
import com.modhtom.fintech_wallet.service.RoundUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/roundups")
@Tag(name = "Round-Up Management", description = "Endpoints for viewing accumulated round-up savings.")
@SecurityRequirement(name = "Bearer Authentication")
public class RoundUpController {
    @Autowired
    RoundUpService roundUpService;


    @Operation(
            summary = "Get user's round-ups",
            description = "Retrieves a paginated list of all round-up amounts saved by the authenticated user."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved round-ups")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @GetMapping
    public ResponseEntity<Page<RoundUpResponseDTO>> getRoundUps(
            @AuthenticationPrincipal UserPrincipal userDetails,
            Pageable pageable) {
        Page<RoundUpResponseDTO> roundUps = roundUpService.getRoundUpsForUser(userDetails.getUsername(), pageable);

        return ResponseEntity.ok(roundUps);
    }
}
