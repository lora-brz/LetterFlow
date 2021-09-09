package ru.example.letterflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.letterflow.domain.dto.MessageDto;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

//    @Autowired
//    private MessageService messageService;

    @PostMapping
    public String createMessage(){
        return "Сообщение отправлено";
    }

    @GetMapping
    public List<MessageDto> getAllMessages(Long roomId){
        return null;
    }

}
