package ru.example.letterflow.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "roomId")
    private Long roomId;

    @Column(name = "personal")
    private Boolean personal;

    @Column(name = "roomName")
    private String roomName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_userId", nullable = false)
    private Long userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomId")
    private List<Message> messages;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Boolean getPersonal() {
        return personal;
    }

    public void setPersonal(Boolean personal) {
        this.personal = personal;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Room() {
    }

    public Room(Long roomId, Long userId, Boolean personal, String roomName) {
        this.roomId = roomId;
        this.userId = userId;
        this.personal = personal;
        this.roomName = roomName;
    }
}
