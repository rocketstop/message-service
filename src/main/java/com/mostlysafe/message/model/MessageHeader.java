package com.mostlysafe.message.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MessageHeader {

    private String id;
    private String recipient;
    private List<String> method;
    private Date received;
    private Date read;
    private Date deleted;
    private Message message;

    public MessageHeader() {}

    public MessageHeader(String recipient,
                         List<String> method,
                         Date received,
                         Date read,
                         Date deleted,
                         Message message) {
        this.recipient = recipient;
        this.method = method;
        this.received = received;
        this.read = read;
        this.deleted = deleted;
        this.message = message;

    }

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(final String recipient) {
        this.recipient = recipient;
    }

    public List<String> getMethod() {
        return method;
    }

    public void setMethod(final List<String> method) {
        this.method = method;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(final Date received) {
        this.received = received;
    }

    public Date getRead() {
        return read;
    }

    public void setRead(final Date read) {
        this.read = read;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(final Date deleted) {
        this.deleted = deleted;
    }

    public Message getMessage(){
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "id='" + id + '\'' +
                ", recipient='" + recipient + '\'' +
                ", received=" + received +
                ", read=" + read +
                ", deleted=" + deleted +
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
        final MessageHeader that = (MessageHeader) o;
        return Objects.equals(id, that.id) && Objects.equals(recipient, that.recipient)
                && Objects.equals(received, that.received) && Objects.equals(read, that.read)
                && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipient, received, read, deleted);
    }


}
