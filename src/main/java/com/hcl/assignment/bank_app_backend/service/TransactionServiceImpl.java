package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.AccountStatementEntryDto;
import com.hcl.assignment.bank_app_backend.dto.AccountStatementRequestDto;
import com.hcl.assignment.bank_app_backend.dto.TransferRequestDto;
import com.hcl.assignment.bank_app_backend.model.Account;
import com.hcl.assignment.bank_app_backend.model.Transaction;
import com.hcl.assignment.bank_app_backend.model.TransactionType;
import com.hcl.assignment.bank_app_backend.repository.AccountRepository;
import com.hcl.assignment.bank_app_backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void transferFunds(TransferRequestDto transferRequestDto) {
        // 1. Fetch and validate from and to accountNumbers
        Account fromAccount = accountRepository.findByAccountNumber(transferRequestDto.fromAccountNumber())
                .orElseThrow(() -> new RuntimeException("From account number not found"));

        Account toAccount = accountRepository.findByAccountNumber(transferRequestDto.toAccountNumber())
                .orElseThrow(() -> new RuntimeException("To account not found"));

        // 2. Check for insufficient balance in fromAccount
        if (fromAccount.getBalance().compareTo(transferRequestDto.amount()) < 0)  {
            throw new RuntimeException("Insufficient funds in from account: " + fromAccount.getAccountNumber());
        }

        // 3. Perform the debit and credit
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequestDto.amount()));
        toAccount.setBalance(toAccount.getBalance().add(transferRequestDto.amount()));

        // 4. Save updated balances in from and to account
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // 5. Create a Transaction
        Transaction history = new Transaction();
        history.setFromAccount(fromAccount);
        history.setToAccount(toAccount);
        history.setAmount(transferRequestDto.amount());
        history.setTimestamp(LocalDateTime.now());
        history.setDescription(transferRequestDto.description());

        transactionRepository.save(history);
    }

    @Override
    public List<AccountStatementEntryDto> getMonthlyStatement(AccountStatementRequestDto accountStatementRequestDto) {

        String accountNumber = accountStatementRequestDto.accountNumber();
        int month = accountStatementRequestDto.month();
        int year = accountStatementRequestDto.year();

        // 1. Validate account exists
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found to create statement: " + accountNumber));

        // 2. Define the date range for the month
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        // 3. Fetch transactions from the repository
        List<Transaction> transactions = transactionRepository.findTransactionsForPeriodByAccountNumber(
                account.getAccountNumber(), startOfMonth, endOfMonth);

        // 4. Map transactions to Statement DTOs
        return transactions.stream().map(t -> {
            boolean isDebit = t.getFromAccount().getAccountNumber().equals(accountNumber);

            return new AccountStatementEntryDto(
                    t.getTimestamp(),
                    t.getDescription(),
                    t.getAmount(),
                    isDebit ? TransactionType.DEBIT : TransactionType.CREDIT,
                    isDebit ? t.getToAccount().getAccountNumber() : t.getFromAccount().getAccountNumber()
            );
        }).toList();
    }

}
