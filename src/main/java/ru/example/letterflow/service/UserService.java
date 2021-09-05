package ru.example.letterflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.example.letterflow.entity.User;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.model.UserModel;
import ru.example.letterflow.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User registration(User user) throws UserAlreadyExistException {
        if(userRepo.findByUserLogin(user.getLogin()) != null){
            throw new UserAlreadyExistException("Такой логин уже занят");
        }
        return userRepo.save(user);
    }

    public UserModel getOne (Long userId) throws UserNotFoundException {
        User user = userRepo.findById(userId).get();
        if(user == null){
            throw new UserNotFoundException("Пользователь не найден");
        }
        return UserModel.toModel(user);
    }
}
