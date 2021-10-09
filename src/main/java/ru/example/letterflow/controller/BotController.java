package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Enum.Role;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.*;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.service.RoomService;
import ru.example.letterflow.service.UserService;

@RestController
@RequestMapping("/bot")
public class BotController {

    private final RoomService roomService;
    private final RoomRepo roomRepo;
    private final UserService userService;

    @Autowired
    public BotController(RoomService roomService, RoomRepo roomRepo, UserService userService) {
        this.roomService = roomService;
        this.roomRepo = roomRepo;
        this.userService = userService;
    }

    @GetMapping("/room_create_${roomName}")
    @PreAuthorize("hasAuthority('user')")
    public RoomDto createRoom(@RequestBody User user,
                              @PathVariable String roomName) throws InsufficientAccessRightsException, RoomAlreadyExistException {
        return roomService.createRoom(user, roomName, false);
    }

    @GetMapping("/room_create_${roomName}-p")
    @PreAuthorize("hasAuthority('user')")
    public RoomDto createPersonalRoom(@RequestBody User user,
                              @PathVariable String roomName) throws InsufficientAccessRightsException, RoomAlreadyExistException {
        return roomService.createRoom(user, roomName, true);
    }

    @DeleteMapping("/room_remove_${roomName}")
    @PreAuthorize("hasAuthority('user')")
    public String removeRoom(@RequestBody User user,
                             @RequestParam String roomName) throws InsufficientAccessRightsException {
        return roomService.deleteRoom(user, roomRepo.findByRoomName(roomName));
    }

    @PutMapping("/room_rename_${roomName}")
    @PreAuthorize("hasAuthority('user')")
    public RoomDto renameRoom(@RequestBody User user,
                              @RequestBody Room room,
                              @PathVariable String roomName) throws InsufficientAccessRightsException, RoomAlreadyExistException {
        return roomService.renameRoom(user, room, roomName);
    }

    @PutMapping("/room_connect_${roomName}")
    @PreAuthorize("hasAuthority('user')")
    public UserDto connectRoom(@RequestParam Long userId,
                              @PathVariable String roomName,
                              @RequestParam String login) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.addUserInRoom(userId, roomName, login);
    }
    @PutMapping("/room_connect_${roomName}-l${login}")
    @PreAuthorize("hasAuthority('user')")
    public UserDto inRoom(@RequestParam Long userId,
                              @PathVariable String roomName,
                              @PathVariable String login) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.addUserInRoom(userId, roomName, login);
    }

    @PutMapping("/room_disconnect_${roomName}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto disconnectRoom(@RequestParam Long userId,
                             @PathVariable String roomName) throws InsufficientAccessRightsException, UserNotFoundException, ImpossibleActionException {
        return userService.deleteUserInRoom(userId, roomName, userService.findUserById(userId).getLogin());
    }

    @PutMapping("/room_disconnect_${roomName}-l${login}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto outRoom(@RequestParam Long userId,
                             @PathVariable String roomName,
                             @PathVariable String login) throws InsufficientAccessRightsException, UserNotFoundException, ImpossibleActionException {
        return userService.deleteUserInRoom(userId, roomName, login);
    }

    @PutMapping ("/user_rename_${newLogin}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto renameUser(@RequestParam Long userId,
                              @PathVariable String newLogin) throws UserAlreadyExistException, UserNotFoundException, InsufficientAccessRightsException {
        return userService.editName(userId, userService.findUserById(userId).getLogin(), newLogin);
    }

    @PutMapping ("/user_rename_${login}_${newLogin}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto renameUser(@RequestParam Long userId,
                              @PathVariable String login,
                              @PathVariable String newLogin) throws UserAlreadyExistException, UserNotFoundException, InsufficientAccessRightsException {
        return userService.editName(userId, login, newLogin);
    }

    @PutMapping("/user_moderator_${login}-n")
    @PreAuthorize("hasAuthority('moderator')")
    public UserDto makeModerator(@PathVariable String login) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.editRole(userService.findUserByLogin(login).getUserId(), login, Role.MODERATOR.name());
    }

    @PutMapping("/user_moderator_${login}-d")
    @PreAuthorize("hasAuthority('moderator')")
    public UserDto makeUser(@PathVariable String login) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.editRole(userService.findUserByLogin(login).getUserId(), login, Role.USER.name());
    }
}
