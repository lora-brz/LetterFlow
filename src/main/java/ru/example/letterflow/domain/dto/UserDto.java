package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Enum.Role;

import java.util.Date;
import java.util.List;

public class UserDto {
    private Long userId;
    private String login;
    private String password;
    private Role role;
    private List<RoomDto> rooms;
    private Date updated;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public boolean isAdmin(){
        return this.getRole() == Role.ADMIN;
    }

    public boolean isModerator(){
        return this.getRole() == Role.MODERATOR;
    }

    public boolean isBlocked(){
        return this.getRole() == Role.BLOCKED;
    }

    public String getPassword() {
        return password;
    }

    public Object getStatus() {
        return null;
    }

    public Date getUpdated() {
        return updated;
    }
}
