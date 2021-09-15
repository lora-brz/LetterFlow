package ru.example.letterflow.domain.dto;

import java.util.ArrayList;
import java.util.Date;

public class MessageDto {
    private Long userId;
    private Long roomId;
    private Date createDate;
    private ArrayList<String> text;

    public MessageDto() {
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
        return new Date();
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}
