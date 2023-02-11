package com.mostlysafe.message;

import java.util.List;
import java.util.logging.Logger;

import com.mostlysafe.message.model.Message;
import com.mostlysafe.message.service.MessageRepository;
import com.mostlysafe.message.service.MessageSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name= "message", description= "An api for retrieving, managing, and sending messages")
public class MessageController {

    private final Logger logger = Logger.getLogger(getClass().getName());
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
    @Operation(summary = "Retrieve all messages",
            description = "Retrieve all messages",
            tags = { "message" })
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
    @Operation(summary = "Retrieve a single message by %messageId%",
            description = "Retrieves a single message by %messageId%",
            tags = { "message" })
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
    @Operation(summary = "Update the message with a given %messageId%",
            description = "Updates a message by merging the current state of the message " +
                    "identified by %messageId% with the state of the message provided in the request body. " +
                    "This method will ignore any id updates.",
            tags = { "message" })
    @RequestMapping(
            value= "/message/{messageId}",
            method= RequestMethod.PATCH,
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
    @Operation(summary = "Deletes a single message with given %messageId%",
            description = "Deletes a single message with given %messageId%",
            tags = { "message" })
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
    @Operation(summary = "Create a message using the supplied message attributes",
            description = "Create a message using the supplied message attributes " +
                    "Object ids (message and header) are optional and will be " +
                    "auto-generated if not provided",
            tags = { "message" })
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
    @Operation(summary = "Retrieve all messages sent by a specific sender",
            description = "Retrieve all messages sent by a specific sender",
            tags = { "message" })
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
    @Operation(summary = "Retrieves all messages for a given recipient",
            description = "Retrieves all messages for a given recipient",
            tags = { "message" })
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
    @Operation(summary = "Creates and sends a message",
            description = "Creates and sends a message, " +
                    "Object ids (message and header) are optional and will be " +
                    "auto-generated if not provided",
            tags = { "message" })
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
