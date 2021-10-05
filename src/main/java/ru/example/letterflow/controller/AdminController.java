package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.service.UserService;

@RestController
@RequestMapping(value = "/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users/{userId}")
    public UserDto getUserById(@PathVariable(name = "userId") Long userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }

}
