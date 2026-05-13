package com.hcl.assignment.bank_app_backend.repository;

import com.hcl.assignment.bank_app_backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);
}
