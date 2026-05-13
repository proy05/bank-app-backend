package com.hcl.assignment.bank_app_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AccountStatementRequestDto(
        @NotBlank(message = "Account number is required")
        String accountNumber,
        @Min(value = 1, message = "Month must be at least 1 (January)")
        @Max(value = 12, message = "Month cannot be greater than 12 (December)")
        int month,
        @Min(value = 2000, message = "Year must be 2000 or later")
        @Max(value = 2030, message = "Year cannot be in the far future")
        int year) {
}
