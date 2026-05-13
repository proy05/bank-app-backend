package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.model.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;

public record UserRegistrationRequestDto(
        @NotBlank(message = "First name cannot be blank")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        String lastName,
        @NotNull(message = "Gender cannot be null")
        Gender gender,
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,
        @NotNull
        @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
        String panNumber,
        @Past(message = "Birth date must be in the past")
        LocalDate dateOfBirth,
        @NotNull(message = "Account type cannot be null")
        AccountType accountType
        ) {

        public boolean hasMinimumAge(int minAge) {
          return dateOfBirth != null &&
                 Period.between(dateOfBirth, LocalDate.now()).getYears() >= minAge;
        }

}
