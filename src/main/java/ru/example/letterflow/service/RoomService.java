package ru.example.letterflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.repository.UserRepo;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private UserRepo userRepo;

    public Room createRoom(Room room, Long userId){
        User user = userRepo.findById(userId).get();
        room.setUserId(user.getUserId());
        return roomRepo.save(room);
    }

    public Room renameRoom(Long roomId, String name){
        Room room = roomRepo.findById(roomId).get();
        room.setRoomName(name);
        return roomRepo.save(room);
    }
}
