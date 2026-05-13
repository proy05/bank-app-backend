package com.hcl.assignment.bank_app_backend.repository;

import com.hcl.assignment.bank_app_backend.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions where the given account number was either the from account
     * OR to account for the given period
     */
    @Query("""
        SELECT t FROM Transaction t 
        WHERE (t.fromAccount.accountNumber = :accountNumber OR t.toAccount.accountNumber = :accountNumber)
        AND t.timestamp BETWEEN :startDate AND :endDate 
        ORDER BY t.timestamp DESC
    """)
    Page<Transaction> findTransactionsForPeriodByAccountNumber(
            @Param(value="accountNumber") String accountNumber,
            @Param(value="startDate") LocalDateTime startDate,
            @Param(value="endDate") LocalDateTime endDate,
            Pageable pageable);  //Controls LIMIT and OFFSET
}
