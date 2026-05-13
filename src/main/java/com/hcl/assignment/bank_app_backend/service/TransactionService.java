package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.AccountStatementEntryDto;
import com.hcl.assignment.bank_app_backend.dto.AccountStatementRequestDto;
import com.hcl.assignment.bank_app_backend.dto.TransferRequestDto;
import org.springframework.data.domain.Page;

public interface TransactionService {
    void transferFunds(TransferRequestDto transferRequestDto);

    Page<AccountStatementEntryDto> getMonthlyStatement(AccountStatementRequestDto accountStatementRequestDto,
                                                       int page, int size) ;


}
