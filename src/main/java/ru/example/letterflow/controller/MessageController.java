package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.User;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

//    @Autowired
//    private MessageService messageService;

    @PostMapping
    public MessageDto createMessage(@RequestBody Message message,
                                    @RequestParam Long userId,
                                    @RequestParam Long roomId){
        return null;
    }

    @GetMapping
    public List<MessageDto> getAllMessages (@RequestParam Long roomId){
        return null;
    }

    @DeleteMapping
    public MessageDto deleteAllMessage (@RequestParam Long roomId){
        return null;
    }

}
