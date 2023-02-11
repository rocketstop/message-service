package com.mostlysafe.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.mostlysafe.message.model.Message;
import com.mostlysafe.message.model.MessageHeader;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MessageRepository {

    private final Logger logger = Logger.getLogger(getClass().getName());
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

        for (MessageHeader h: message.getHeaders()){
            if (null == h.getId() || h.getId().trim().isEmpty()){
                h.setId(UUID.randomUUID().toString());
            }

            h.setMessageId(message.getId());
        }

        messageStore.put(message.getId(), message);
    }

    public Message updateMessage(String messageId, Message message){

        if (null == message.getId() || message.getId().trim().isEmpty()) {
            message.setId(messageId);
        }

        if (messageId.equals(message.getId())){
            if (messageStore.containsKey(messageId)) {

                Message currentMessage = messageStore.get(messageId);
                BeanUtils.copyProperties(message, currentMessage);
            }
        }
        return message;
    }

    public List<Message> findBySender(String senderId) {
        List<Message> messages = messageStore.values().stream()
                .filter(m -> m.getSender().equals(senderId))
                .collect(Collectors.toList());

        return messages;
    }

    public List<Message> findByRecipient(String recipientId) {
        List<MessageHeader> matchingHeaders = messageStore.values().stream()
                .flatMap(m -> m.getHeaders().stream())
                .filter(h -> h.getRecipient().equals(recipientId))
                .collect(Collectors.toList());

        logger.info("matching headers: "+ matchingHeaders);

        return matchingHeaders.stream()
                .map(MessageHeader::getMessageId)
                .map(messageStore::get)
                .collect(Collectors.toList());

    }
}

// TODO: findByRecipient returns all headers, should it only return headers for the recipient?
