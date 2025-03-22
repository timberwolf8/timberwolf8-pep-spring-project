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

    /**
     * Creates a new message if:
     * - messageText is not blank.
     * - messageText is within 255 characters.
     * - postedBy refers to a valid user.
     * Returns the saved message or null if validation fails.
     */
    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() || 
            message.getMessageText().length() > 255 || 
            !accountRepository.existsById(message.getPostedBy())) {
            return null;
        }
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages from the database.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its ID.
     */
    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    /**
     * Deletes a message by its ID.
     * Returns 1 if the message existed and was deleted, otherwise returns 0.
     */
    public int deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    /**
     * Updates the text of a message if:
     * - The message exists.
     * - The new messageText is not blank and within 255 characters.
     * Returns 1 if update was successful, otherwise returns 0.
     */
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

    /**
     * Retrieves all messages posted by a specific user.
     */
    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
