package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.Room;

import java.util.List;

public class RoomDto {
    private Long roomId;
    private Long userId;
    private String roomName;

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
}
