package com.hcl.assignment.bank_app_backend.service;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

        import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hcl.assignment.bank_app_backend.dto.UserAccountDto;
import com.hcl.assignment.bank_app_backend.dto.UserRegistrationRequestDto;
import com.hcl.assignment.bank_app_backend.exception.UserAlreadyExistsException;
import com.hcl.assignment.bank_app_backend.model.*;
        import com.hcl.assignment.bank_app_backend.repository.AccountRepository;
import com.hcl.assignment.bank_app_backend.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveNewUser_UserAlreadyExistsByEmail() {
        // Arrange
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto(
                "John", "Doe", Gender.MALE, "john@example.com", "ABCDE1234F", LocalDate.of(1990, 1, 1), AccountType.SAVINGS
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> userService.saveNewUser(dto));
        assertEquals("User already exists with email john@example.com", exception.getMessage());
    }

    @Test
    void testSaveNewUser_UserAlreadyExistsByPanNumber() {
        // Arrange
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto(
                "John", "Doe", Gender.MALE, "john@example.com", "ABCDE1234F", LocalDate.of(1990, 1, 1), AccountType.SAVINGS
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByPanNumber(dto.panNumber())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> userService.saveNewUser(dto));
        assertEquals("User already exists with pan number:ABCDE1234F", exception.getMessage());
    }
}