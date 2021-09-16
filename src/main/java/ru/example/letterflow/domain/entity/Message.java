package ru.example.letterflow.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "messageId", nullable = false)
    private Long messageId;

    @Column(name = "userId")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "room_roomId", nullable = false)
    private Long roomId;

    @Column(name = "createDate")
    private final Date createDate;

    @Column(name = "text")
    private String text;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message(Long messageId, Long userId, Long roomId, Date createDate, String text) {
        this.messageId = messageId;
        this.userId = userId;
        this.roomId = roomId;
        this.createDate = new Date();
        this.text = text;
    }
}
