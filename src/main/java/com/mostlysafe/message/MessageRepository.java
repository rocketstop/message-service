package com.mostlysafe.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.mostlysafe.message.model.MessageBody;
import org.springframework.stereotype.Component;

@Component
public class MessageRepository {
    private final ConcurrentHashMap<String, MessageBody> messages;

    public MessageRepository() {
        messages = new ConcurrentHashMap<>();
    }

    public Map<String, MessageBody> getMessages() {
        return messages;
    }

    public Optional<MessageBody> findById(String messageId){
        return Optional.ofNullable(messages.get(messageId));
    }

    public List<MessageBody> getAll() {
        return new ArrayList<MessageBody>(messages.values());
    }
}
