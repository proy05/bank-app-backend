package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.AccountStatementEntryDto;
import com.hcl.assignment.bank_app_backend.dto.AccountStatementRequestDto;
import com.hcl.assignment.bank_app_backend.dto.TransferRequestDto;

import java.util.List;

public interface TransactionService {
    void transferFunds(TransferRequestDto transferRequestDto);

    List<AccountStatementEntryDto> getMonthlyStatement(AccountStatementRequestDto accountStatementRequestDto) ;


}
