package ru.example.letterflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Enum.Permission;
import ru.example.letterflow.domain.entity.Enum.Status;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.InsufficientAccessRightsException;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.service.mapping.RoomMapper;
import ru.example.letterflow.service.mapping.UserMapper;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto registrationUser(UserDto userDto) throws UserAlreadyExistException {
        if (userRepo.findByUserLogin(userDto.getLogin()) != null){
            throw new UserAlreadyExistException("Такой логин уже занят");
        }
        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        user.setPermission(Permission.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto addUserInRoom(UserDto userDto, RoomDto roomDto, Long userId) throws InsufficientAccessRightsException {
        if(userDto.isBlocked()){
            throw new InsufficientAccessRightsException("Вы заблокированы и не можете добавить пользователя в чат");
        }
        User user = userRepo.findById(userId).get();
        Room room = RoomMapper.ROOM_MAPPER.toEntity(roomDto);
        user.getRooms().add(room);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto deleteUserInRoom(UserDto userDto, RoomDto roomDto, Long userId) throws InsufficientAccessRightsException {
        if(!userDto.isAdmin()){
            throw new InsufficientAccessRightsException("Удалять пользователей из чата может только администратор");
        }
        Room deleteRoom = RoomMapper.ROOM_MAPPER.toEntity(roomDto);
        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        List<Room> rooms = user.getRooms();
        for(Room room : rooms){
            if(room.getRoomId() == deleteRoom.getRoomId()){
                rooms.remove(deleteRoom);
            }
        }
        user.setRooms(rooms);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
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
    public UserDto editName (UserDto userDto, String string) throws UserAlreadyExistException {
        if(userRepo.findByUserLogin(string) != null){
            throw new UserAlreadyExistException("Такой логин уже занят");
        }
//        получить юзера из дто или из репозитория?
//        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        User user = userRepo.findById(userDto.getUserId()).get();
        user.setLogin(string);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto editPassword(UserDto userDto, String string){
//        получить юзера из дто или из репозитория?
//        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        User user = userRepo.findById(userDto.getUserId()).get();
        user.setPassword(string);
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public UserDto editPermission(UserDto userDto, Permission permission, Long userId) throws InsufficientAccessRightsException {
//        получить юзера из дто или из репозитория?
//        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        User user = userRepo.findById(userId).get();
        if (userDto.isAdmin()){
            user.setPermission(permission);
        } else if(userDto.isModerator()){
            if(permission == Permission.ADMIN || permission == Permission.MODERATOR){
                throw new InsufficientAccessRightsException("Вы не можете менять администратора или модератора");
            }
            user.setPermission(permission);
        } else{
            throw new InsufficientAccessRightsException("Вы не можете менять роль пользователя");
        }
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional
    public String deleteUser(UserDto userDto){
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
