package com.hcl.assignment.bank_app_backend.controller;

import com.hcl.assignment.bank_app_backend.dto.*;
import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.service.TransactionService;
import com.hcl.assignment.bank_app_backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final TransactionService transactionService;

    @Autowired
    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    /**
     * Create a new user and their account according to the type of account requested
     */
    @PostMapping("/users")
    public ResponseEntity<String> signup(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {

        if(!userRegistrationRequestDto.hasMinimumAge(18)){
            return new ResponseEntity<>("User must be at least 18 years old", HttpStatus.BAD_REQUEST);
        }
        UserAccountCreationDto userAccountCreationDto = userService.saveNewUser(userRegistrationRequestDto);

        return ResponseEntity.ok(String.format(
                        "User id %d created successfully. Account number of %s account is %s",
                        userAccountCreationDto.userId(),
                        userAccountCreationDto.accountType(),
                        userAccountCreationDto.accountNumber()
                )
        );
    }

    /**
     * Create a new account for an existing user
     */
    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<String> createAccountForExistingUser(@PathVariable Long userId,
                                                               @Valid @RequestBody
                                                               ExistingUserAccountCreationRequestDto existingUserAccountCreationRequestDto){
        UserAccountCreationDto userAccountCreationDto = userService.createAccountForExistingUser(userId,
                existingUserAccountCreationRequestDto.accountType());

        return ResponseEntity.ok(String.format(
                        "%s account created for user id %d. Account number is %s",
                        userAccountCreationDto.accountType(),
                        userAccountCreationDto.userId(),
                        userAccountCreationDto.accountNumber()
                )
        );
    }

    /**
     * Transfer funds from one account to another
     */
    @PostMapping("/transactions/transfers")
    public ResponseEntity<String> transferFunds(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        transactionService.transferFunds(transferRequestDto);
        return ResponseEntity.ok("Transfer successful");
    }

    @PostMapping("/statement/view")
    public ResponseEntity<Page<AccountStatementEntryDto>> getMonthlyStatement(
            @Valid @RequestBody AccountStatementRequestDto accountStatementRequestDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<AccountStatementEntryDto> statement = transactionService.getMonthlyStatement(
                accountStatementRequestDto, page, size);

        return ResponseEntity.ok(statement);
    }




}
