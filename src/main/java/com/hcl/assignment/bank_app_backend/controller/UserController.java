package com.hcl.assignment.bank_app_backend.controller;

import com.hcl.assignment.bank_app_backend.dto.ExistingUserAccountCreationRequestDto;
import com.hcl.assignment.bank_app_backend.dto.UserAccountCreationDto;
import com.hcl.assignment.bank_app_backend.dto.UserRegistrationRequestDto;
import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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





}
