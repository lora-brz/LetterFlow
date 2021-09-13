package ru.example.letterflow.domain.dto;

import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private Long userId;
    private String login;
    private Character character;
    private List<RoomDto> rooms;

    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setLogin(user.getLogin());
        userDto.setCharacter(user.getCharacter());
        userDto.setRooms(user.getRooms().stream().map(RoomDto::toDTO).collect(Collectors.toList()));
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

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }
}
