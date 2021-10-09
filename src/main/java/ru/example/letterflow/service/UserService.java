package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.AuthenticationRequestDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Enum.Role;
import ru.example.letterflow.domain.entity.Enum.Status;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.ImpossibleActionException;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.repository.RoomRepo;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.security.jwt.JwtTokenProvider;
import ru.example.letterflow.service.mapping.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto saveUser(UserDto userDto) throws UserAlreadyExistException {
        if (userRepo.findByUserLogin(userDto.getLogin()) != null){
            throw new UserAlreadyExistException("Такой логин уже занят");
        }
        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public ResponseEntity<?> loginUser(AuthenticationRequestDto requestDto, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager){
        try{
            String login = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            User user = userRepo.findByUserLogin(login);
            String token = jwtTokenProvider.createToken(login, user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException ex){
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    @Transactional(readOnly = true)
    public UserDto findUserByLogin(String login) throws UserNotFoundException {
        User user = userRepo.findByUserLogin(login);
        if(user == null){
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto findUserById(Long userId) throws UserNotFoundException {
        User user = userRepo.findById(userId).get();
        if(user == null){
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll(){
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = null;
        for(User user : users){
            userDtos.add(UserMapper.USER_MAPPER.toDto(user));
        }
        return userDtos;
    }

    @Transactional
    public UserDto addUserInRoom(Long userId, String roomName, String login) throws InsufficientAccessRightsException, UserNotFoundException {

        if(findUserById(userId).isBlocked()){
            throw new InsufficientAccessRightsException("Вы заблокированы и не можете добавить пользователя в чат");
        }
        if(findUserByLogin(login).isBlocked()){
            throw new InsufficientAccessRightsException("Вы не можете добавить заблокированного пользователя в чат");
        }

        User user = userRepo.findById(userId).get();
        Room room = roomRepo.findByRoomName(roomName);
        user.getRooms().add(room.getRoomId());
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto deleteUserInRoom(Long userId, String roomName, String login) throws InsufficientAccessRightsException, UserNotFoundException, ImpossibleActionException {
        Room room = roomRepo.findByRoomName(roomName);
        if(!findUserById(userId).isAdmin() || !userId.equals(room.getUserId())){
            throw new InsufficientAccessRightsException("Удалять пользователей из чата может только владелец или администратор");
        }
        if(Objects.equals(userId, room.getUserId())){
            throw new ImpossibleActionException("Вы не можете покинуть чат, т.к вы его владелец");
        }

        User user = UserMapper.USER_MAPPER.toEntity(findUserByLogin(login));
        List<Long> rooms = user.getRooms();
        Long roomId = room.getRoomId();
        for(Long id : rooms){
            if(id.equals(roomId)){
                rooms.remove(roomId);
            }
        }
        user.setRooms(rooms);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto editName (Long userId,String login, String newLogin) throws UserAlreadyExistException, UserNotFoundException, InsufficientAccessRightsException {
        if(!findUserById(userId).isAdmin() || !userId.equals(findUserByLogin(login).getUserId())){
            throw new InsufficientAccessRightsException("Вы не можете менять имя пользователю " + login);
        }
        if(userRepo.findByUserLogin(newLogin) != null){
            throw new UserAlreadyExistException("Такой логин уже занят");
        }
        User user = userRepo.findByUserLogin(login);
        user.setLogin(newLogin);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto editPassword(Long userId, String string)  {

        User user = userRepo.findById(userId).get();
        user.setPassword(passwordEncoder.encode(string));
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto editRole(Long userId, String login, String userRole) throws InsufficientAccessRightsException, UserNotFoundException {
        if(!Objects.equals(login, userRepo.findByUserLogin(login))){
            throw new UserNotFoundException ("Пользователь " + login + " не найден");
        }
        User user = userRepo.findById(userId).get();
        Map<String, Role> roles = new HashMap<>();
        roles.put("admin", Role.ADMIN);
        roles.put("moderator", Role.MODERATOR);
        roles.put("user", Role.USER);
        roles.put("blocked", Role.BLOCKED);
        Role role = roles.get(userRole);

        if (user.getRole().equals(Role.ADMIN)){
            user.setRole(role);
        } else if(user.getRole().equals(Role.MODERATOR)){
            if(role == Role.ADMIN || role == Role.MODERATOR){
                throw new InsufficientAccessRightsException("Вы не можете менять администратора или модератора");
            }
            user.setRole(role);
        } else{
            throw new InsufficientAccessRightsException("Вы не можете менять роль пользователя");
        }
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public String deleteUser(Long userId, UserDto userDto) throws InsufficientAccessRightsException {
        User user = userRepo.findById(userId).get();
        if (!user.getRole().equals(Role.ADMIN) || !userId.equals(userDto.getUserId())){
            throw new InsufficientAccessRightsException("Вы не можете удалять пользователей");
        }
        userRepo.deleteById(userDto.getUserId());
        return "Пользователь удален";
    }

//    public User saveUser (User user) throws UserAlreadyExistException {
//        if(userRepo.findByUserLogin(user.getLogin()) != null){
//            throw new UserAlreadyExistException("Такой логин уже занят");
//        }
//        return userRepo.save(user);
//    }
//
//    public UserDto getOneUser (Long userId) throws UserNotFoundException {
//        User user = userRepo.findById(userId).get();
//        if(user == null){
//            throw new UserNotFoundException("Пользователь не найден");
//        }
//        return UserDto.toDto(user);
//    }
//
//    public Long deleteUser(Long userId){
//        userRepo.deleteById(userId);
//        return userId;
//    }

}
