package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.entity.Enum.Role;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.repository.MessageRepo;
import ru.example.letterflow.service.mapping.MessageMapper;

import java.util.List;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Transactional
    public MessageDto addMessage(User user, Room room, Message message, String text) throws InsufficientAccessRightsException {
        if(user.getRole().equals(Role.BLOCKED)){
            throw new InsufficientAccessRightsException("Заблокированный пользователь не может отправлять сообщения");
        }
        message.setUserId(user.getUserId());
        message.setRoomId(room.getRoomId());
        message.setText(text);
        messageRepo.save(message);
        return MessageMapper.MESSAGE_MAPPER.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<String> findAllMessagesByRoom(Room room, User user) throws InsufficientAccessRightsException {
        if(!user.getUserId().equals(room.getUserId())){
            throw new InsufficientAccessRightsException("Вы не можете читать сообщения из данного чата");
        }
        List<Message> messages = messageRepo.findAll();
        List<String> messagesInRoom = null;
        for(Message message : messages){
            if(message.getRoomId() == room.getRoomId())
                messagesInRoom.add(message.getText());
        }
        return messagesInRoom;
    }

    @Transactional
    public String deleteMessage(User user, Room room, Long messageId) throws InsufficientAccessRightsException {
        if(!user.getRole().equals(Role.ADMIN) || !user.getRole().equals(Role.MODERATOR) || !user.getUserId().equals(room.getUserId())){
            throw new InsufficientAccessRightsException("Удалять сообщения в этом чате может только его владелец, администратор или модератор");
        }
        messageRepo.deleteById(messageId);
        return "Сообщение удалено";
    }

//    @Transactional
//    public String deleteAllMessagesByUser(UserDto userDto){
//        messageRepo.deleteById(userDto.getUserId());
//        return "Сообщения пользователя " + userDto.getLogin() + " удалены";
//    }
}
