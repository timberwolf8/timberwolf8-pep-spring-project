package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // 1. User Registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        Account createdAccount = accountService.registerAccount(account);
        if (createdAccount != null) {
            return ResponseEntity.ok(createdAccount);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // 2. User Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        Account authenticatedAccount = accountService.authenticate(account);
        if (authenticatedAccount != null) {
            return ResponseEntity.ok(authenticatedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 3. Create a new Message
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            return ResponseEntity.ok(createdMessage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 4. Retrieve all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // 5. Retrieve a message by its ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return ResponseEntity.of(messageService.getMessageById(messageId));
    }

    // 6. Delete a message by its ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        int deletedRows = messageService.deleteMessage(messageId);
        if (deletedRows > 0) {
            return ResponseEntity.ok(deletedRows);
        }
        return ResponseEntity.ok().build(); // Returning 200 with empty body if message doesn't exist
    }

    // 7. Update a message's text
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        int updatedRows = messageService.updateMessageText(messageId, message.getMessageText());
        if (updatedRows > 0) {
            return ResponseEntity.ok(updatedRows);
        }
        return ResponseEntity.badRequest().build();
    }

    // 8. Retrieve all messages posted by a particular user
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}
