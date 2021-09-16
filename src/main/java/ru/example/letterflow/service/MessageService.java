package ru.example.letterflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.repository.MessageRepo;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.service.mapping.MessageMapper;
import ru.example.letterflow.service.mapping.RoomMapper;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;
    
    @Transactional
    public MessageDto addMessage(MessageDto messageDto){
        Message message = MessageMapper.MESSAGE_MAPPER.toEntity(messageDto);
        messageRepo.save(message);
        return MessageMapper.MESSAGE_MAPPER.toDto(message);
    }

    public List<MessageDto> findAllMessagesByRoom(MessageDto messageDto, Long roomId){
        List<Message> messages = messageRepo.findAll();
        List<MessageDto> messageDtos = null;
        for(Message message : messages){
            if(message.getRoomId() == roomId)
                messageDtos.add(MessageMapper.MESSAGE_MAPPER.toDto(message));
        }
        return messageDtos;
    }

    public String deleteAllMessagesByRoom(MessageDto messageDto, Long roomId){
        messageRepo.deleteById(messageDto.getRoomId());
        return "Чат очищен";
    }        
}
