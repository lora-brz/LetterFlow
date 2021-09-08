package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.service.UserService;

import java.util.List;

public class MessageController {

//    @Autowired
//    private MessageService messageService;

    @PostMapping
    public String saveMessage(){
        return "Сообщение отправлено";
    }

    @GetMapping
    public List<MessageDto> getAllMessages(Long roomId){
        return null;
    }

}
