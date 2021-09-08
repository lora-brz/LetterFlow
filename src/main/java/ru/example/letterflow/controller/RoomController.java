package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

//    @Autowired
//    private RoomService roomService;

    @PostMapping
    public String createRoom(){
        return "Чат создан";
    }

    @GetMapping
    public List<RoomDto> getAllRooms(Long userId){
        return null;
    }

    @PutMapping
    public String renameRoom(Long roomId){
        return "Имя чата изненено на";
    }

    @DeleteMapping
    public String deleteRoom(Long roomId){
        return "Чат удален";
    }
}
