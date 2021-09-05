package ru.example.letterflow.repository;

import org.springframework.data.repository.CrudRepository;
import ru.example.letterflow.entity.User;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUserLogin(String login);
}
