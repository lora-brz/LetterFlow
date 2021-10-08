package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.exceptions.RoomAlreadyExistException;
import ru.example.letterflow.service.RoomService;
import ru.example.letterflow.service.mapping.RoomMapper;
import ru.example.letterflow.service.mapping.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user')")
    public RoomDto createRoom(@RequestBody Room room,
                              @RequestBody User user) throws InsufficientAccessRightsException, RoomAlreadyExistException {
        return roomService.createRoom(RoomMapper.ROOM_MAPPER.toDto(room), UserMapper.USER_MAPPER.toDto(user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('every')")
    public List<RoomDto> getAllRooms(){
        return roomService.findAllRooms();
    }

    @PutMapping("/rename/${roomName}")
    @PreAuthorize("hasAuthority('user')")
    public RoomDto renameRoom(@RequestBody User user,
                              @RequestBody Room room,
                              @PathVariable String roomName) throws InsufficientAccessRightsException, RoomAlreadyExistException {
        return roomService.renameRoom(user, room, roomName);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('user')")
    public String deleteRoom(@RequestBody User user,
                              @RequestBody Room room) throws InsufficientAccessRightsException {
        return roomService.deleteRoom(user, room);
    }

//    @PostMapping
//    public ResponseEntity createRoom(@RequestBody Room room,
//                                     @RequestParam Long userId){
//        try{
//            return ResponseEntity.ok(roomService.createRoom(room, userId));
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Произошла ошибка при создании чата");
//        }
//    }
//
//    @PutMapping
//    public ResponseEntity renameRoom(@RequestParam Long roomId, String name){
//        try{
//            return ResponseEntity.ok(roomService.renameRoom(roomId, name));
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Ошибка! Не удалось переименовать чат");
//        }
//    }
}
