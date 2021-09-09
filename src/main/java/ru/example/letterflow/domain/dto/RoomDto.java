package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Message;

import java.util.List;

public class RoomDto {
    private Long userId;
    private Boolean personal;
    private String roomName;
    private List<Message> messages;
}
