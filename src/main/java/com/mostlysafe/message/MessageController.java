package com.mostlysafe.message;

import java.util.List;
import java.util.logging.Logger;

import com.mostlysafe.message.model.Message;
import com.mostlysafe.message.service.MessageRepository;
import com.mostlysafe.message.service.MessageSender;
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
    MessageSender messageSender;

    @Autowired
    public MessageController(MessageRepository messageRepository,
                             MessageSender messageSender){
        this.messageRepository = messageRepository;
        this.messageSender = messageSender;
        logger.info("MessageController loaded.");
    }

    /***
     * Get all messages
     * @return
     */
    @RequestMapping(
            value= "/messages",
            method= RequestMethod.GET,
            produces= "application/json"
    )
    public List<Message> getAll() {
        logger.info("message-service getAll() invoked.");
        List<Message> messageList = messageRepository.getAll();
        logger.info("message-service getAll() found messages: " + messageList.size());

        return messageList;
    }

    /***
     * Get a single message by messageId
     * @param messageId
     * @return
     */
    @RequestMapping(
            value= "/message/{messageId}",
            method= RequestMethod.GET,
            produces= "application/json"
    )
    public Message findById(@PathVariable("messageId") String messageId){
        logger.info("message-service findById() invoked.");
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId));
        logger.info("message-service findById() found: "+ message);

        return message;
    }

    @RequestMapping(
            value= "/message/{messageId}",
            method= RequestMethod.PUT,
            consumes= "application/json",
            produces= "application/json"
    )
    public Message updateMessage(@PathVariable("messageId") String messageId,
                                 @RequestBody Message message){
        logger.info("message-service updateMessage() invoked.");
        Message newMessage = messageRepository.updateMessage(messageId, message);
        logger.info("message-service updateMessage() found: "+ message);

        return newMessage;
    }

    /***
     * Delete a message
     * @param messageId
     * @return
     */
    @RequestMapping(
            value = "/message/{messageId}",
            method= RequestMethod.DELETE,
            produces= "application/json"
    )
    public Message deleteMessage(@PathVariable("messageId") String messageId){
        logger.info("message-service deleteMessage() invoked.");
        Message message = messageRepository.deleteMessage(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId));
        logger.info("message-service deleteMessage() found: "+ message);

        return message;
    }

    /***
     * Create a message (but do not send it)
     * @param message
     * @return
     */
    @RequestMapping(
            value= "/message",
            method= RequestMethod.POST,
            consumes= "application/json",
            produces= "application/json"
    )
    public Message addNewMessage(@RequestBody Message message){
        logger.info("creating new message.");
        messageRepository.addMessage(message);
        logger.info("New Message created: "+ message.getId());

        return message;
    }

    /***
     * Find all messages sent by a specific sender
     * @param senderId
     * @return
     */
    @RequestMapping(value="/message/{senderId}/findBySender",
            method= RequestMethod.GET,
            produces= "application/json"
    )
    public List<Message> findBySender(@PathVariable("senderId") String senderId){
        logger.info("message-service findBySender() invoked.");
        List<Message> messageList = messageRepository.findBySender(senderId);
        logger.info("message-service findBySender() found messages: " + messageList.size());

        return messageList;

    }

    /***
     * Find all messages sent to a specific recipient
     * @param recipientId
     * @return
     */
    @RequestMapping(
            value="/message/{recipientId}/findByRecipient",
            method= RequestMethod.GET,
            produces= "application/json"
    )
    public List<Message> findByRecipient(@PathVariable("recipientId") String recipientId){
        logger.info("message-service findByRecipient() invoked.");
        List<Message> messageList = messageRepository.findByRecipient(recipientId);
        logger.info("message-service findByRecipient() found messages: " + messageList.size());

        return messageList;

    }

    /***
     * Send a new message
     * @param message
     * @return
     */
    @RequestMapping(
            value="/message/send",
            method= RequestMethod.POST,
            consumes= "application/json",
            produces= "application/json"
    )
    public Message sendNewMessage(@RequestBody Message message){
        logger.info("creating new message.");
        messageRepository.addMessage(message);
        logger.info("New Message created: "+ message.getId());
        messageSender.send(message.getId());

        return message;

    }

}
