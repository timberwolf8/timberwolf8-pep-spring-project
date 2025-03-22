package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class SocialMediaController {

    private final MessageService messageService;

    public SocialMediaController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Optional<Message> createdMessage = messageService.createMessage(message);
        return createdMessage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return messageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        int deletedRows = messageService.deleteMessage(messageId);
        return deletedRows > 0 ? ResponseEntity.ok(deletedRows) : ResponseEntity.ok().build();
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        int updatedRows = messageService.updateMessageText(messageId, message.getMessageText());
        return updatedRows > 0 ? ResponseEntity.ok(updatedRows) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}
