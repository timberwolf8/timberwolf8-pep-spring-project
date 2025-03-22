package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<Message> createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() ||
            message.getMessageText().length() > 255 || !accountRepository.existsById(message.getPostedBy())) {
            return Optional.empty();
        }
        Message savedMessage = messageRepository.save(message);
        return Optional.of(savedMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    public int deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1; // One row affected
        }
        return 0; // No rows affected
    }

    public int updateMessageText(int messageId, String newMessageText) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent() && newMessageText != null && !newMessageText.trim().isEmpty() &&
            newMessageText.length() <= 255) {
            Message message = optionalMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
