package ru.example.letterflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.service.mapping.RoomMapper;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Transactional
    public RoomDto addRoom(RoomDto roomDto){
        Room room = RoomMapper.ROOM_MAPPER.toEntity(roomDto);
        roomRepo.save(room);
        return RoomMapper.ROOM_MAPPER.toDto(room);
    }

    @Transactional(readOnly = true)
    public List<RoomDto> findAllRoomsByUser(Long userId){
        List<Room> rooms = roomRepo.findAll();
        List<RoomDto> roomDtos = null;
        for(Room room : rooms){
            if(room.getUserId() == userId)
                roomDtos.add(RoomMapper.ROOM_MAPPER.toDto(room));
        }
        return roomDtos;
    }

    @Transactional
    public RoomDto renameRoom(RoomDto roomDto, String string){
        Room room = RoomMapper.ROOM_MAPPER.toEntity(roomDto);
        room.setRoomName(string);
        return RoomMapper.ROOM_MAPPER.toDto(room);
    }

    @Transactional
    public String deleteRoom(RoomDto roomDto){
        roomRepo.deleteById(roomDto.getRoomId());
        return "Чат удален";
    }
}
