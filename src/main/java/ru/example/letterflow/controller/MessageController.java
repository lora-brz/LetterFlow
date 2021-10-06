package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    @PreAuthorize("hasAuthority('send message')")
    public MessageDto createMessage(@RequestBody Message message,
                                    @RequestParam Long userId,
                                    @RequestParam Long roomId){
        return null;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read message')")
    public List<MessageDto> getAllMessages (@RequestParam Long roomId){
        return null;
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('delete message')")
    public MessageDto deleteMessage (@RequestParam Long messageId){
        return null;
    }

}
