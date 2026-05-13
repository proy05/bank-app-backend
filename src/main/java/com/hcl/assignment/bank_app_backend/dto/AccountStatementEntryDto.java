package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountStatementEntryDto(
        LocalDateTime timestamp,
        String description,
        BigDecimal amount,
        TransactionType transactionType, // "DEBIT" or "CREDIT"
        String otherAccountNumber)
{
}
