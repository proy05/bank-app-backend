package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.AccountType;

public record UserAccountCreationDto(String accountNumber,
                                     AccountType accountType,
                                     Long userId
) {
}
