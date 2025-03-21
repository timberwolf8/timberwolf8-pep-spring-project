package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText(). || message.getMessageText().length() > 255) {
            return 
        }

        if (message.getPostedBy() == null || !messageRepository.existsById(message.getPostedBy())) {
            return ResponseEntity.status(400).build();
        }

        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.status(200).body(savedMessage);
    }

    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.status(200).body(messages);
    }

    public ResponseEntity<Message> getMessageById(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            return ResponseEntity.status(200).body(message.get());
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Integer> updateMessage(int messageId, String newMessageText) {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent() && newMessageText != null && !newMessageText.trim().isEmpty() && newMessageText.length() <= 255) {
            Message updatedMessage = message.get();
            updatedMessage.setMessageText(newMessageText);
            messageRepository.save(updatedMessage);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).build();
    }

    public ResponseEntity<Integer> deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<List<Message>> getMessagesByUser(int accountId) {
        List<Message> messages = messageRepository.findByPostedBy(accountId);
        return ResponseEntity.status(200).body(messages);
    }
}
