package com.hcl.assignment.bank_app_backend.service;

import com.hcl.assignment.bank_app_backend.dto.UserAccountCreationDto;
import com.hcl.assignment.bank_app_backend.dto.UserRegistrationRequestDto;
import com.hcl.assignment.bank_app_backend.exception.UserAlreadyExistsException;
import com.hcl.assignment.bank_app_backend.model.Account;
import com.hcl.assignment.bank_app_backend.model.AccountType;
import com.hcl.assignment.bank_app_backend.model.User;
import com.hcl.assignment.bank_app_backend.repository.AccountRepository;
import com.hcl.assignment.bank_app_backend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository= accountRepository;
    }

    @Override
    public UserAccountCreationDto saveNewUser(UserRegistrationRequestDto userRegistrationRequestDto) {
        //check the user pan and email are unique
        if(userRepository.existsByEmail(userRegistrationRequestDto.email())){
            throw new UserAlreadyExistsException("User already exists with email " +
                    userRegistrationRequestDto.email());
        }

        if(userRepository.existsByPanNumber(userRegistrationRequestDto.panNumber())){
            throw new UserAlreadyExistsException("User already exists with pan number:"+
                    userRegistrationRequestDto.panNumber());
        }

        //Create and save the User
        User user = new User();
        BeanUtils.copyProperties(userRegistrationRequestDto, user);
        userRepository.save(user);

        //Create and save the Account for this User
        String accountNumber = createAccount(userRegistrationRequestDto.accountType(), user);

        //Success - return account number
        return new UserAccountCreationDto(accountNumber,
                userRegistrationRequestDto.accountType(),
                user.getId()) ;

    }

    /**
     * Create and saves an account for a given user and given account type
     */
    @Override
    public String createAccount(AccountType accountType,
                              User user) {

        //Generate a unique 12-digit account number
        String accountNumber;
        while(true){
            accountNumber = generateAccountNumber();
            if(!accountRepository.existsByAccountNumber(accountNumber))
                break;
        }

        //Create an Account using the above account number

        Account account = new Account(accountNumber, accountType);
        account.setBalance(new BigDecimal(10000)); //Set initial balance to Rs 10000

        //Associate the User with the Account
        account.setUser(user);

        //Save the Account
        accountRepository.save(account);

        return accountNumber;

    }

    @Override
    public UserAccountCreationDto createAccountForExistingUser(Long userId, AccountType accountType) {
        //Find the User by userId, Else throw exception
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User id " +
                userId + "not found"));

        //Generate a unique 12-digit account number
        String accountNumber = createAccount(accountType, user);

        //Success - return account number
        return new UserAccountCreationDto(accountNumber,
                accountType,
                user.getId()) ;
    }

    public String generateAccountNumber() {
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            // Generates a digit between 0 and 9
            int digit = ThreadLocalRandom.current().nextInt(10); //Generate random digit between 0 and 9
            sb.append(digit);
        }
        return sb.toString();
    }



}
