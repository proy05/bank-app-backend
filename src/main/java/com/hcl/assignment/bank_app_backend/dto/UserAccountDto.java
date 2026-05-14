package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.AccountType;

import java.math.BigDecimal;

public record UserAccountDto(String accountNumber,
                             AccountType accountType,
                             BigDecimal balance,
                             Long userId) {
}
