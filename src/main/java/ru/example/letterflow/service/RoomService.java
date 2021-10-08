package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.exceptions.RoomAlreadyExistException;
import ru.example.letterflow.repository.MessageRepo;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.service.mapping.RoomMapper;
import ru.example.letterflow.service.mapping.UserMapper;

import java.util.List;

@Service
@Slf4j
public class RoomService {

    private final RoomRepo roomRepo;
    private final MessageRepo messageRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo, MessageRepo messageRepo, UserRepo userRepo) {
        this.roomRepo = roomRepo;
        this.messageRepo = messageRepo;
    }

    @Transactional
    public RoomDto createRoom(RoomDto roomDto, UserDto userDto) throws RoomAlreadyExistException, InsufficientAccessRightsException {
        if(userDto.isBlocked()){
            throw new InsufficientAccessRightsException("Заблокированные пользователи не могут создавать комнаты");
        }
        if(roomRepo.findByRoomName(roomDto.getRoomName()) != null){
            throw new RoomAlreadyExistException("Комната с таким именем уже существует");
        }
        Room room = RoomMapper.ROOM_MAPPER.toEntity(roomDto);
        room.setUserId(userDto.getUserId());
        roomRepo.save(room);
        return RoomMapper.ROOM_MAPPER.toDto(room);
    }

    @Transactional(readOnly = true)
    public List<RoomDto> findAllRooms(){
        List<Room> rooms = roomRepo.findAll();
        List<RoomDto> roomDtos = null;
        for(Room room : rooms){
            roomDtos.add(RoomMapper.ROOM_MAPPER.toDto(room));
        }
        return roomDtos;
    }

    @Transactional
    public RoomDto renameRoom(User user, Room room, String string) throws RoomAlreadyExistException, InsufficientAccessRightsException {
        UserDto userDto = UserMapper.USER_MAPPER.toDto(user);
        if(!userDto.isAdmin() || !user.getUserId().equals(room.getUserId())){
            throw new InsufficientAccessRightsException("Переименовывать комнату может только владелец или администратор");
        }
        if(roomRepo.findByRoomName(string) != null){
            throw new RoomAlreadyExistException("Комната с таким именем уже существует");
        }
        room.setRoomName(string);
        return RoomMapper.ROOM_MAPPER.toDto(room);
    }

    @Transactional
    public String deleteRoom(User user, Room room) throws InsufficientAccessRightsException {
        if(!user.getUserId().equals(room.getUserId())){
            throw new InsufficientAccessRightsException("Чат может удалить только его создатель");
        }
        roomRepo.deleteById(room.getRoomId());
        return "Чат удален";
    }
}
