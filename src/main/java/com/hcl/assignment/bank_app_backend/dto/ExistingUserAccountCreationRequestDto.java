package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.AccountType;
import jakarta.validation.constraints.NotNull;

public record ExistingUserAccountCreationRequestDto( @NotNull AccountType accountType) {
}
