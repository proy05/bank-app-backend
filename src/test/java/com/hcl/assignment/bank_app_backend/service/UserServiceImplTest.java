package com.hcl.assignment.bank_app_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hcl.assignment.bank_app_backend.dto.UserAccountResponseDto;
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

    @Test
    void testSaveNewUser() {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto(
                "John", "Doe", Gender.MALE, "john@example.com", "ABCDE1234F",
                LocalDate.of(1990, 1, 1), AccountType.SAVINGS
        );

        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setGender(dto.gender());
        user.setEmail(dto.email());
        user.setPanNumber(dto.panNumber());
        user.setDateOfBirth(dto.dateOfBirth());
        user.setId(1L); // Simulate generated ID

        Account account = new Account("123456789012", AccountType.SAVINGS);
        account.setBalance(new BigDecimal(10000));
        account.setUser(user);

        //Stubbing repository methods
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByPanNumber(dto.panNumber())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        UserAccountResponseDto result = userService.saveNewUser(dto);

        assertNotNull(result);
        assertEquals("123456789012", result.accountNumber());
        assertEquals(AccountType.SAVINGS, result.accountType());
        assertEquals(new BigDecimal(10000), result.balance());
        assertEquals(1L, result.userId());
    }
}