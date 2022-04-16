package com.mostlysafe.message.service;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageSender {

    protected Logger logger = Logger.getLogger(getClass().getName());

    public MessageSender(){

    }

    public void send(String messageId){
        logger.info("Sending message: "+ messageId);

    }
}
