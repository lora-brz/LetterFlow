package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Enum.Permission;

import java.util.List;

public class UserDto {
    private Long userId;
    private String login;
    private Permission permission;
    private List<RoomDto> rooms;

    public UserDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public boolean isAdmin(){
        return this.getPermission() == Permission.ADMIN;
    }

    public boolean isModerator(){
        return this.getPermission() == Permission.MODERATOR;
    }

    public boolean isBlocked(){
        return this.getPermission() == Permission.BLOCKED;
    }
}
