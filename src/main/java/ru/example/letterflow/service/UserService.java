package ru.example.letterflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.repository.UserRepo;
import ru.example.letterflow.service.mapping.UserMapper;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public UserDto addUser (UserDto userDto) throws UserAlreadyExistException {
        if (userRepo.findByUserLogin(userDto.getLogin()) != null){
            throw new UserAlreadyExistException("Такой логин уже занят");
        }
        User user = UserMapper.USER_MAPPER.toEntity(userDto);
        user.s
        userRepo.save(user);
        return UserMapper.USER_MAPPER.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto findOne(UserDto userDto){
        return UserMapper.USER_MAPPER.toDto(userRepo.findById(userDto.getUserId()).get());
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll(){
        List<User> user = userRepo.findAll();
        List<UserDto> userDtos = null;
        for(User el : user){
            userDtos.add(UserMapper.USER_MAPPER.toDto(el));
        }
        return userDtos;
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
