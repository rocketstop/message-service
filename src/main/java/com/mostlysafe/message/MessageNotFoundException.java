package com.mostlysafe.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public MessageNotFoundException() { super();}
    public MessageNotFoundException(final String messageId) {
        super("No such message: " + messageId);
    }
}
