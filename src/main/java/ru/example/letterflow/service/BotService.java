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

    public String roomCommand(User user, Map<String, String> mapCommand) throws InsufficientAccessRightsException, RoomAlreadyExistException, UserNotFoundException, ImpossibleActionException {
        String result = null;
        if(mapCommand.get("action").equals("create")){
            roomService.createRoom(user, mapCommand.get("roomName"), Boolean.valueOf(mapCommand.get("personal")));
            result = "Комната '" + mapCommand.get("roomName") + "' создана";
        }
        if(mapCommand.get("action").equals("remove")){
            roomService.deleteRoom(user, roomRepo.findByRoomName(mapCommand.get("roomName")));
            result = "Комната '" + mapCommand.get("roomName") + "' удалена";
        }
        if(mapCommand.get("action").equals("rename")) {
            roomService.renameRoom(user, roomRepo.findByRoomName(mapCommand.get("roomName")), mapCommand.get("newRoomName"));
            result = "Комната '" + mapCommand.get("roomName") + "' переименована на '" + mapCommand.get("newRoomName") +"'";
        }
        if(mapCommand.get("action").equals("connect")){
            if(Boolean.parseBoolean(mapCommand.get("login"))){
                userService.addUserInRoom(user.getUserId(), mapCommand.get("roomName"), mapCommand.get("login"));
                result = "Пользователь '" + mapCommand.get("login") + "' добавлен в комнату '" + mapCommand.get("roomName") + "'";
            } else{
                userService.addUserInRoom(user.getUserId(), mapCommand.get("roomName"), user.getLogin());
                result = "Вы зашли в комнату '" + mapCommand.get("roomName") + "'";
            }
        }
        if(mapCommand.get("action").equals("disconnect")){
            if(Boolean.parseBoolean(mapCommand.get("login"))){
                userService.deleteUserInRoom(user.getUserId(), mapCommand.get("roomName"), mapCommand.get("login"));
                result = "Пользователь '" + mapCommand.get("login") + "' удален из комнаты '" + mapCommand.get("roomName") + "'";
            } else{
                userService.deleteUserInRoom(user.getUserId(), mapCommand.get("roomName"), user.getLogin());
                result = "Вы вышли из комнаты '" + mapCommand.get("roomName") + "'";
            }
        }
        return result;
    }

    public String userCommand(User user, Map<String, String> mapCommand) throws UserNotFoundException, InsufficientAccessRightsException, UserAlreadyExistException {
        String result = null;
        if(mapCommand.get("action").equals("rename")){
            if(Boolean.parseBoolean(mapCommand.get("newLogin"))) {
                userService.editName(user.getUserId(), mapCommand.get("login"), mapCommand.get("newLogin"));
                result = "Пользователь '" + mapCommand.get("login") +"' переименован на '" + mapCommand.get("newLogin") + "'";
            }else{
                userService.editName(user.getUserId(), user.getLogin(), mapCommand.get("login"));
                result = "Ваш логин изменен на '" + mapCommand.get("login") +"'";
            }
        }
        if(mapCommand.get("action").equals("ban")){
            userService.cleanRooms(user, mapCommand.get("login"));
            userService.editRole(user.getUserId(), mapCommand.get("login"), "blocked");
            result = "Пользователь '" + mapCommand.get("login") +"' заблокирован и удален из всех комнат";
        }
        if(mapCommand.get("action").equals("moderator")){
            userService.editRole(user.getUserId(), mapCommand.get("login"), mapCommand.get("role"));
            String roleToAnswer = mapCommand.get("role").equals("moderator") ? "модератором" : "обычным пользователем";
            result = "Пользователь '" + mapCommand.get("login") + "' является " + roleToAnswer;
        }
        return result;
    }

    public String botCommand(User user, Map<String, String> mapCommand){
        String result = null;

        return  result;
    }
}
