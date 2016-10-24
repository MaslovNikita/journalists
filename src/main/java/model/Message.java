package model;

import java.sql.Date;

/**
 * Created by homie on 10.10.16.
 */
public class Message {
    private int receiverId;
    private int senderId;
    private String message;
    private Date sendingTime;
    private int id;
    private boolean viewed;
    private boolean deleted;

    public Message() {
    }

    public Message(final int receiverId, final int senderId, final String message, final Date sendingTime, final int id, final boolean viewed, final boolean deleted) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
        this.sendingTime = sendingTime;
        this.id = id;
        this.viewed = viewed;
        this.deleted = deleted;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(final int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(final int senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(final Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(final boolean viewed) {
        this.viewed = viewed;
    }

    public void set(Message message){
        this.receiverId = message.receiverId;
        this.senderId = message.senderId;
        this.message = message.message;
        this.sendingTime = message.sendingTime;
        this.id = message.id;
        this.viewed = message.viewed;
        this.deleted = message.deleted;
    }
}
