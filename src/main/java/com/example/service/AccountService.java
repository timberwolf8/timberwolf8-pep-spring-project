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
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return null; 
        }

        return accountRepository.save(account);
    }

    public Optional<Account> login(String username, String password) {
        Optional<Account> account = accountRepository.findByUsername(username);
        
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account;
        }
        
        return Optional.empty();  
    }
}
