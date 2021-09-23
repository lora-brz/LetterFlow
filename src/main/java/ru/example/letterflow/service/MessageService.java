package ru.example.letterflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.repository.MessageRepo;
import ru.example.letterflow.service.mapping.MessageMapper;


@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Transactional
    public MessageDto addMessage(UserDto userDto, RoomDto roomDto, MessageDto messageDto) throws InsufficientAccessRightsException {
        if(userDto.isBlocked()){
            throw new InsufficientAccessRightsException("Заблокированный пользователь не может отправлять сообщения");
        }
        Message message = MessageMapper.MESSAGE_MAPPER.toEntity(messageDto);
        message.setUserId(userDto.getUserId());
        message.setRoomId(roomDto.getRoomId());
        messageRepo.save(message);
        return MessageMapper.MESSAGE_MAPPER.toDto(message);
    }

    @Transactional
    public String deleteAllMessagesByRoom(UserDto userDto, RoomDto roomDto) throws InsufficientAccessRightsException {
        if(!userDto.isAdmin() && !userDto.isModerator()){
            throw new InsufficientAccessRightsException("Удалять сообщения в этом чате может только его администратор или модератор");
        }
        messageRepo.deleteById(roomDto.getRoomId());
        return "Чат очищен";
    }

    @Transactional
    public String deleteAllMessagesByUser(UserDto userDto){
        messageRepo.deleteById(userDto.getUserId());
        return "Сообщения пользователя " + userDto.getLogin() + " удалены";
    }
}
