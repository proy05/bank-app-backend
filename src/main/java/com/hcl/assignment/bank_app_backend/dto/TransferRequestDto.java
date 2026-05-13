package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.Account;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDto(
        @NotNull(message = "Source account ID is required")
        String fromAccountNumber,

        @NotNull(message = "Destination account ID is required")
        String toAccountNumber,

        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,

        String description
) { }
