package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.status(200).body(savedAccount);
    }

    public ResponseEntity<Account> loginAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

        if (existingAccount.isPresent() && existingAccount.get().getPassword().equals(account.getPassword())) {
            return ResponseEntity.status(200).body(existingAccount.get());
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
