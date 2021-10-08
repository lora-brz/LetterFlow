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

    @PutMapping ("/edit/login/${login}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto editUserLogin(@RequestParam Long userId,
                                 @PathVariable String login) throws UserAlreadyExistException {
        return userService.editName(userId, login);
    }

    @PutMapping ("/edit/pass/${pass)")
    @PreAuthorize("hasAuthority('every')")
    public UserDto editUserPass(@RequestParam Long userId,
                                @PathVariable String pass) {
        return userService.editPassword(userId, pass);
    }

    @PutMapping("/room/in/${roomName}/${login}")
    @PreAuthorize("hasAuthority('user')")
    public UserDto inviteRoom(@RequestParam Long userId,
                              @PathVariable String roomName,
                              @PathVariable String login) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.addUserInRoom(userId, roomName, login);
    }

    @PutMapping("/room/out/${roomName}/${login}")
    @PreAuthorize("hasAuthority('every')")
    public UserDto leaveRoom(@RequestParam Long userId,
                             @PathVariable String roomName,
                             @PathVariable String login) throws InsufficientAccessRightsException, UserNotFoundException, ImpossibleActionException {
        return userService.deleteUserInRoom(userId, roomName, login);
    }


    @PutMapping("/role/${login}/${role}")
    @PreAuthorize("hasAuthority('moderator')")
    public UserDto changeRole(@RequestParam Long userId,
                              @PathVariable String login,
                              @PathVariable String role) throws InsufficientAccessRightsException, UserNotFoundException {
        return userService.editRole(userId, login, role);
    }

    @DeleteMapping("/delete/${login}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteUser(@RequestParam Long userId,
            @PathVariable String login) throws UserNotFoundException, InsufficientAccessRightsException {
        UserDto userDto = userService.findUserByLogin(login);
        return userService.deleteUser(userId, userDto);
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
