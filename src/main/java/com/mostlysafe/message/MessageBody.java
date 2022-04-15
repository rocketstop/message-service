package com.mostlysafe.message;

import java.util.Objects;

public class MessageBody {

    private String id;
    private String sender;
    private String subject;
    private String body;

    public MessageBody(){}

    public MessageBody(String sender, String subject, String body){
        this.sender = sender;
        this.subject= subject;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(final String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageBody that = (MessageBody) o;
        return Objects.equals(id, that.id)
                && Objects.equals(sender, that.sender)
                && Objects.equals(subject, that.subject)
                && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, subject, body);
    }
}
