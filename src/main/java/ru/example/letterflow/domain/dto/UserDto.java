package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;

import java.util.List;

public class UserDto {
    private Long userId;
    private String login;
    private Character character;
    private List<Room> rooms;

    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setLogin(user.getLogin());
        userDto.setCharacter(user.getCharacter());
        userDto.setRooms(user.getRooms());
        return userDto;
    }

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

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
