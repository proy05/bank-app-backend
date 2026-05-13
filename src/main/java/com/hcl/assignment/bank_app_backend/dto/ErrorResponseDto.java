package com.hcl.assignment.bank_app_backend.dto;

import java.util.Map;

public record ErrorResponseDto(
        int status,
        String message,
        Map<String, String> errors
) {

}
