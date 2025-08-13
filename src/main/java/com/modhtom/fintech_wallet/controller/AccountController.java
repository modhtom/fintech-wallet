package com.modhtom.fintech_wallet.controller;

import com.modhtom.fintech_wallet.DTO.AccountRequestDTO;
import com.modhtom.fintech_wallet.DTO.AccountResponseDTO;
import com.modhtom.fintech_wallet.model.UserPrincipal;
import com.modhtom.fintech_wallet.service.AccountService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Account Management", description = "Endpoints for managing user accounts.")
@SecurityRequirement(name = "Bearer Authentication")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Operation(
            summary = "Create a new account",
            description = "Creates a new financial account (e.g., checking, savings) for the authenticated user."
    )
    @ApiResponse(responseCode = "201", description = "Account successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid account data provided")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(
            @RequestBody AccountRequestDTO AccountRequestDTO,
            @AuthenticationPrincipal UserPrincipal userDetails) {

        AccountResponseDTO responseDTO = accountService.createAccount(AccountRequestDTO, userDetails.getUsername());
        if(responseDTO==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
