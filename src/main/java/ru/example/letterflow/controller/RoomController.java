package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public RoomDto createRoom(@RequestBody Room room,
                              @RequestParam Long userId){
        return null;
    }

    @GetMapping
    public List<RoomDto> getAllRooms(@RequestParam Long userId){
        return null;
    }

    @PutMapping
    public RoomDto renameRoom(@RequestParam Long roomId){
        return null;
    }

    @DeleteMapping
    public RoomDto deleteRoom(@RequestParam Long roomId){
        return null;
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
