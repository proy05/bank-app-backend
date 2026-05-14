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
    void testSaveNewUser() {
        // Arrange
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto(
                "John", "Doe", Gender.MALE, "john@example.com", "ABCDE1234F", LocalDate.of(1990, 1, 1), AccountType.SAVINGS
        );

        User savedUser = new User();
        savedUser.setFirstName(dto.firstName());
        savedUser.setLastName(dto.lastName());
        savedUser.setGender(dto.gender());
        savedUser.setEmail(dto.email());
        savedUser.setPanNumber(dto.panNumber());
        savedUser.setDateOfBirth(dto.dateOfBirth());
        savedUser.setId(1L); // Simulate generated ID

        Account account = new Account("123456789012", AccountType.SAVINGS);
        account.setBalance(new BigDecimal(10000));
        account.setUser(savedUser);

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByPanNumber(dto.panNumber())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        UserAccountDto result = userService.saveNewUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals("123456789012", result.accountNumber());
        assertEquals(AccountType.SAVINGS, result.accountType());
        assertEquals(new BigDecimal(10000), result.balance());
        assertEquals(1L, result.userId());
//
//        verify(userRepository).existsByEmail(dto.email());
//        verify(userRepository).existsByPanNumber(dto.panNumber());
//        verify(userRepository).save(any(User.class));
//        verify(accountRepository).existsByAccountNumber(anyString());
//        verify(accountRepository).save(any(Account.class));
    }

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