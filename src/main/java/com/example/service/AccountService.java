package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerUser(Account account) {
        // Check if the username already exists
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null; // If username exists, return null (conflict)
        }

        // Save the new account
        return accountRepository.save(account);
    }

    public Optional<Account> login(String username, String password) {
        // Find the account by username
        Optional<Account> account = accountRepository.findByUsername(username);
        
        // Check if password matches
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account;
        }
        
        return Optional.empty();  // Return empty if credentials are invalid
    }
}
