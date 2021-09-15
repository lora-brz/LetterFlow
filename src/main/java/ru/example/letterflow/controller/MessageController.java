package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.MessageDto;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

//    @Autowired
//    private MessageService messageService;

    @PostMapping
    public MessageDto createMessage(Long roomId){
        return null;
    }

    @GetMapping
    public List<MessageDto> getAllMessages (Long roomId){
        return null;
    }

    @DeleteMapping
    public MessageDto deleteAllMessage (Long roomId){
        return null;
    }

}
