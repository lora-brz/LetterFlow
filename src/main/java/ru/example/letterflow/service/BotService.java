package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.*;
import ru.example.letterflow.repository.RoomRepo;

import java.util.Map;

@Service
@Slf4j
public class BotService {
    private final RoomService roomService;
    private final RoomRepo roomRepo;
    private final UserService userService;

    @Autowired
    public BotService(RoomService roomService, RoomRepo roomRepo, UserService userService) {
        this.roomService = roomService;
        this.roomRepo = roomRepo;
        this.userService = userService;
    }

    public void roomCommand(User user, Map<String, String> mapCommand) throws InsufficientAccessRightsException, RoomAlreadyExistException, UserNotFoundException, ImpossibleActionException {
       if(mapCommand.get("action").equals("create")){
            roomService.createRoom(user, mapCommand.get("roomName"), Boolean.valueOf(mapCommand.get("personal")));
        }
        if(mapCommand.get("action").equals("remove")){
            roomService.deleteRoom(user, roomRepo.findByRoomName(mapCommand.get("roomName")));
        }
        if(mapCommand.get("action").equals("rename")) {
            roomService.renameRoom(user, roomRepo.findByRoomName(mapCommand.get("roomName")), mapCommand.get("newRoomName"));
        }
        if(mapCommand.get("action").equals("connect")){
            if(Boolean.parseBoolean(mapCommand.get("login"))){
                userService.addUserInRoom(user.getUserId(), mapCommand.get("roomName"), mapCommand.get("login"));
            } else{
                userService.addUserInRoom(user.getUserId(), mapCommand.get("roomName"), user.getLogin());
            }
        }
        if(mapCommand.get("action").equals("disconnect")){
            if(Boolean.parseBoolean(mapCommand.get("login"))){
                userService.deleteUserInRoom(user.getUserId(), mapCommand.get("roomName"), mapCommand.get("login"));
            } else{
                userService.deleteUserInRoom(user.getUserId(), mapCommand.get("roomName"), user.getLogin());
            }
        }
    }

    public void userCommand(User user, Map<String, String> mapCommand) throws UserNotFoundException, InsufficientAccessRightsException, UserAlreadyExistException {
        if(mapCommand.get("action").equals("rename")){
            if(Boolean.parseBoolean(mapCommand.get("newLogin"))) {
                userService.editName(user.getUserId(), mapCommand.get("login"), mapCommand.get("newLogin"));
            }else{
                userService.editName(user.getUserId(), user.getLogin(), mapCommand.get("login"));
            }
        }
        if(mapCommand.get("action").equals("ban")){
            userService.cleanRooms(user, mapCommand.get("login"));
        }
        if(mapCommand.get("action").equals("moderator")){
            userService.editRole(user.getUserId(), mapCommand.get("login"), mapCommand.get("role"));
        }
    }

    public void botCommand(User user, Map<String, String> mapCommand){

    }
}
