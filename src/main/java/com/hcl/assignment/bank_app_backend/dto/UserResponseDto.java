package com.hcl.assignment.bank_app_backend.dto;

import com.hcl.assignment.bank_app_backend.model.Gender;
import java.time.LocalDate;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String panNumber,
        LocalDate dateOfBirth
) {

}