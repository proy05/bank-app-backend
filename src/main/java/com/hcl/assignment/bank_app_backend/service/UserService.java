package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.UserAccountDto;
import com.hcl.assignment.bank_app_backend.dto.UserRegistrationRequestDto;
import com.hcl.assignment.bank_app_backend.model.Account;
import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.model.User;

import java.util.List;

public interface UserService {

    UserAccountDto saveNewUser(UserRegistrationRequestDto userRegistrationRequestDto);

    Account createAccount(AccountType accountType,
                          User user);

    String generateAccountNumber();

    UserAccountDto createAccountForExistingUser(Long userId, AccountType accountType);

    List<UserAccountDto> getUserAccounts(Long userId);
}
