package com.modhtom.fintech_wallet.controller;

import com.modhtom.fintech_wallet.DTO.TransactionRequestDTO;
import com.modhtom.fintech_wallet.DTO.TransactionResponseDTO;
import com.modhtom.fintech_wallet.model.UserPrincipal;
import com.modhtom.fintech_wallet.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction Management", description = "Endpoints for creating and viewing transactions.")
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Operation(
            summary = "Get user transactions",
            description = "Retrieves a paginated and optionally filtered list of transactions for the authenticated user."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactions(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestParam(required = false) String merchant,
            Pageable pageable) {

        Page<TransactionResponseDTO> transactions = transactionService.getTransactionsForUser(
                userDetails.getUsername(), merchant, pageable);

        return ResponseEntity.ok(transactions);
    }

    @Operation(
            summary = "Create a new transaction",
            description = "Records a new transaction for one of the user's accounts. This will trigger a round-up calculation."
    )
    @ApiResponse(responseCode = "201", description = "Transaction successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid transaction data provided")
    @ApiResponse(responseCode = "403", description = "User does not have permission to access the specified account")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @PostMapping("create")
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO transactionRequestDTO,
            @AuthenticationPrincipal UserPrincipal userDetails) {

        TransactionResponseDTO responseDTO = transactionService.createTransaction(transactionRequestDTO, userDetails);
        if(responseDTO==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
