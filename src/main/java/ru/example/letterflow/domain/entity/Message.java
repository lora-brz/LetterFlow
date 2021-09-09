package ru.example.letterflow.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "messageId", nullable = false)
    private Long messageId;

    public Long getId() {
        return messageId;
    }

    public void setId(Long id) {
        this.messageId = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_userId", nullable = false)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "room_roomId", nullable = false)
    private Long roomId;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Column(name = "createDate")
    private final Date createDate = new Date();

    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "text")
    private ArrayList<String> text = new ArrayList<>();

    public ArrayList<String> getText() {
        return text;
    }

    public void setText() throws IOException {
        String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
        this.text.add(s);
    }

    public Message() {
    }

    public Message(Long messageId, Long userId, Long roomId) {
        this.messageId = messageId;
        this.userId = userId;
        this.roomId = roomId;
    }
}
