package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.UserAccountResponseDto;
import com.hcl.assignment.bank_app_backend.dto.UserRegistrationRequestDto;
import com.hcl.assignment.bank_app_backend.dto.UserResponseDto;
import com.hcl.assignment.bank_app_backend.model.Account;
import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.model.User;

import java.util.List;

public interface UserService {

    UserAccountResponseDto saveNewUser(UserRegistrationRequestDto userRegistrationRequestDto);

    Account createAccount(AccountType accountType,
                          User user);

    String generateAccountNumber();

    UserAccountResponseDto createAccountForExistingUser(Long userId, AccountType accountType);

    List<UserAccountResponseDto> getUserAccounts(Long userId);

    UserResponseDto findUserById(Long userId);

    List<UserResponseDto> findAllUsers();
}
