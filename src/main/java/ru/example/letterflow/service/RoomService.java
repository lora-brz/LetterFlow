package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.exceptions.RoomAlreadyExistException;
import ru.example.letterflow.repository.MessageRepo;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.service.mapping.RoomMapper;

import java.util.List;

@Service
@Slf4j
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

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

    @Transactional
    public RoomDto renameRoom(UserDto userDto, RoomDto roomDto, String string) throws RoomAlreadyExistException, InsufficientAccessRightsException {
//        получить чат из дто или из репозитория?
//        Room room = RoomMapper.ROOM_MAPPER.toEntity(roomDto);
        if(!userDto.isAdmin()){
            throw new InsufficientAccessRightsException("Переименовывать комнату может только владелец");
        }
        if(roomRepo.findByRoomName(string) != null){
            throw new RoomAlreadyExistException("Комната с таким именем уже существует");
        }
        Room room = roomRepo.findByRoomId(roomDto.getRoomId());
        room.setRoomName(string);
        return RoomMapper.ROOM_MAPPER.toDto(room);
    }

    @Transactional(readOnly = true)
    public List<String> findAllMessagesByRoom(RoomDto roomDto){
        List<Message> messages = messageRepo.findAll();
        List<String> messagesInRoom = null;
        for(Message message : messages){
            if(message.getRoomId() == roomDto.getRoomId())
                messagesInRoom.add(message.getText());
        }
        return messagesInRoom;
    }

    @Transactional
    public String deleteRoom(UserDto userDto, RoomDto roomDto) throws InsufficientAccessRightsException {
        if(userDto.getUserId() != roomDto.getUserId()){
            throw new InsufficientAccessRightsException("Чат может удалить только его создатель");
        }
        roomRepo.deleteById(roomDto.getRoomId());
        return "Чат удален";
    }

//    @Transactional(readOnly = true)
//    public List<RoomDto> findAllRoomsByUser(Long userId){
//        List<Room> rooms = roomRepo.findAll();
//        List<RoomDto> roomDtos = null;
//        for(Room room : rooms){
//            if(room.getUserId() == userId)
//                roomDtos.add(RoomMapper.ROOM_MAPPER.toDto(room));
//        }
//        return roomDtos;
//    }
}
