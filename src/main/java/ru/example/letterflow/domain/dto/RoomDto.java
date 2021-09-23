package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;

import java.util.List;

public class RoomDto {
    private Long roomId;
    private Long userId;
    private String roomName;
    private List<User> roomUsers;

    public RoomDto() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<User> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(List<User> roomUsers) {
        this.roomUsers = roomUsers;
    }
}
