package com.mostlysafe.message;

import java.util.List;
import java.util.logging.Logger;

import com.mostlysafe.message.model.MessageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    protected Logger logger = Logger.getLogger(getClass().getName());
    MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
        logger.info("MessageController loaded.");
    }

    @RequestMapping("/messages")
    public List<MessageBody> getAll() {
        logger.info("message-service getAll() invoked.");
        List<MessageBody> messageList = messageRepository.getAll();
        logger.info("message-service getAll() found messages: " + messageList.size());

        return messageList;
    }

    @RequestMapping("/messages/{messageId}")
    public MessageBody byId(@PathVariable("messageId") String messageId){
        logger.info("mesasge-service byId() invoked.");
        MessageBody message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId));
        logger.info("mesasge-service byId() found: "+ message);

        return message;
    }

    @RequestMapping(value= "/messages", method= RequestMethod.POST)
    public MessageBody addNewMessage(@RequestBody MessageBody messageBody){
        logger.info("creating new messagebody.");
        messageRepository.getMessages().put(messageBody.getId(), messageBody);
        logger.info("New MessageBody created: "+ messageBody.getId());

        return messageBody;
    }
}
