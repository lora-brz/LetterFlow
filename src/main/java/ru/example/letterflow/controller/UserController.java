package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto saveUser(@RequestBody User user){
        return null;
    }

    @GetMapping ("/{userId}")
    public UserDto getOneUser(@PathVariable Long userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.findAll();
    }

    @PutMapping ("/{userId}")
    public UserDto editUser(@PathVariable UserDto userDto, String name) throws UserAlreadyExistException {
        return userService.editName(userDto, name);
    }

    @DeleteMapping
    public UserDto deleteUser(@RequestParam Long userId){
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
