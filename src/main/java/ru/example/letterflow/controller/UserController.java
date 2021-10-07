package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.exceptions.ImpossibleActionException;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/{userId}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto getOneUser(@PathVariable Long userId) throws UserNotFoundException {
        return userService.findUserById(userId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('every')")
    public List<UserDto> getAllUsers(){
        return userService.findAll();
    }

    @PutMapping ("/edit/login")
    @PreAuthorize("hasAuthority('every')")
    public UserDto editUserLogin(@RequestParam Long userId,
                                 Long id, String login) throws UserAlreadyExistException, InsufficientAccessRightsException {
        return userService.editName(userId, id, login);
    }

    @PutMapping ("/edit/pass")
    @PreAuthorize("hasAuthority('every')")
    public UserDto editUserPass(@RequestParam Long userId,
                                Long id, String login) throws InsufficientAccessRightsException {
        return userService.editPassword(userId, id, login);
    }

    @PutMapping("/room/in")
    @PreAuthorize("hasAuthority('user')")
    public UserDto inviteRoom(@RequestParam Long userId,
                                String roomName, String login) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.addUserInRoom(userId, roomName, login);
    }

    @PutMapping("/room/out")
    @PreAuthorize("hasAuthority('every')")
    public UserDto leaveRoom(@RequestParam Long userId,
                             String roomName, String login) throws InsufficientAccessRightsException, UserNotFoundException, ImpossibleActionException {
        return userService.deleteUserInRoom(userId, roomName, login);
    }


    @DeleteMapping("/delete")
    @PreAuthorize("getAuthority('moderator')")
    public UserDto changeRole(@RequestParam Long userId, String login, String role) throws InsufficientAccessRightsException {
        return userService.editRole(userId, login, role);
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
