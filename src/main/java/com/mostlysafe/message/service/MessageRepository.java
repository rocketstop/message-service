package com.mostlysafe.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.mostlysafe.message.model.Message;
import com.mostlysafe.message.model.MessageHeader;
import org.springframework.stereotype.Component;

@Component
public class MessageRepository {

    protected Logger logger = Logger.getLogger(getClass().getName());
    private final ConcurrentHashMap<String, Message> messageStore;

    public MessageRepository() {
        messageStore = new ConcurrentHashMap<>();
    }

    public Optional<Message> findById(String messageId){
        return Optional.ofNullable(messageStore.get(messageId));
    }

    public Optional<Message> deleteMessage(String messageId){
        return Optional.ofNullable(messageStore.remove(messageId));
    }

    public List<Message> getAll() {
        return new ArrayList<>(messageStore.values());
    }

    public void addMessage(Message message){

        if (null == message.getId() || message.getId().trim().isEmpty()){
            message.setId(UUID.randomUUID().toString());
        }

        if (messageStore.containsKey(message.getId())){
            logger.info("Message exists, returning.");
            return;
        }

        messageStore.put(message.getId(), message);
    }

    public List<Message> findBySender(String senderId) {
        List<Message> messages = messageStore.values().stream()
                .filter(m -> m.getSender().equals(senderId))
                .collect(Collectors.toList());

        return messages;
    }

    public List<Message> findByRecipient(String recipientId) {
        List<Message> messages = messageStore.values().stream()
                .filter(m -> m.getHeaders().stream().anyMatch(h -> h.getRecipient().equals(recipientId)))
                .collect(Collectors.toList());

        return messages;
    }
}
