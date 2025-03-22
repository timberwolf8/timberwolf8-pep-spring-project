package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        Account createdAccount = accountService.registerUser(account);
        if (createdAccount == null) {
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(200).body(createdAccount); 
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Account> login(@RequestBody Account loginRequest) {
        Optional<Account> account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Optional<Message> createdMessage = messageService.createMessage(message);
        return createdMessage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return messageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    @RequestMapping(value = "messages/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        int deletedRows = messageService.deleteMessage(messageId);
        return deletedRows > 0 ? ResponseEntity.ok(deletedRows) : ResponseEntity.ok().build();
    }

    @RequestMapping(value = "messages/{messageId}", method = RequestMethod.PATCH)
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        int updatedRows = messageService.updateMessageText(messageId, message.getMessageText());
        return updatedRows > 0 ? ResponseEntity.ok(updatedRows) : ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/accounts/{accountId}/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByUser(accountId);
        return ResponseEntity.ok(messages); 
    }


}
