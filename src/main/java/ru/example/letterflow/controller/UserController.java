package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto saveUser(){
        return null;
    }

    @GetMapping
    public UserDto getOneUser(Long userId){
        return null;
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return null;
    }

    @PutMapping
    public UserDto editUser(Long userId){
        return null;
    }

    @DeleteMapping
    public UserDto deleteUser(){
        return null;
    }

//    @PostMapping
//    public ResponseEntity registration (@RequestBody User user){
//        try{
//            userService.registration(user);
//            return ResponseEntity.ok("Пользователь сохранен");
//        }catch (UserAlreadyExistException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }

//    @GetMapping
//    public ResponseEntity getOneUser(@RequestParam Long userId){
//        try{
//            return ResponseEntity.ok(userService.getOne(userId));
//        }catch (UserNotFoundException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }

//    @DeleteMapping("/{userId}")
//    public ResponseEntity deleteUser(@PathVariable Long userId){
//        try{
//            return ResponseEntity.ok(userService.deleteUser(userId));
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
}
