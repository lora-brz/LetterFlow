package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.entity.Message;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/create/${text}")
    @PreAuthorize("hasAuthority('user')")
    public MessageDto createMessage(@RequestBody Message message,
                                    @RequestBody User user,
                                    @RequestBody Room room,
                                    @PathVariable String text) throws InsufficientAccessRightsException {
        return messageService.addMessage(user, room, message, text);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('every')")
    public List<String> getAllMessages (@RequestBody Room room,
                                            @RequestBody User user) throws InsufficientAccessRightsException {
        return messageService.findAllMessagesByRoom(room, user);
    }

    @DeleteMapping("/delete/${messageId}")
    @PreAuthorize("hasAuthority('user')")
    public String deleteMessage (@RequestBody User user,
                                     @RequestBody Room room,
                                     @PathVariable Long messageId) throws InsufficientAccessRightsException {
        return messageService.deleteMessage(user, room, messageId);
    }
}
