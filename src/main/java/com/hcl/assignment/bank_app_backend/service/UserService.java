package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.UserAccountCreationDto;
import com.hcl.assignment.bank_app_backend.dto.UserRegistrationRequestDto;
import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.model.User;

public interface UserService {

    UserAccountCreationDto saveNewUser(UserRegistrationRequestDto userRegistrationRequestDto);

    String createAccount(AccountType accountType,
                         User user);

    String generateAccountNumber();

    UserAccountCreationDto createAccountForExistingUser(Long userId, AccountType accountType);
}
