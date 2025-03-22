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

    /**
     * Registers a new user account.
     * Registration is successful if:
     * - The username is not blank.
     * - The password is at least 4 characters long.
     * - The username is unique (not already in the database).
     * If registration fails, returns null.
     */
    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null;
        }

        return accountRepository.save(account);
    }

    /**
     * Authenticates a user by verifying the provided username and password.
     * Returns the account if authentication is successful, otherwise returns null.
     */
    public Account authenticate(Account account) {
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

        if (existingAccount.isPresent() && existingAccount.get().getPassword().equals(account.getPassword())) {
            return existingAccount.get();
        }

        return null;
    }
}
