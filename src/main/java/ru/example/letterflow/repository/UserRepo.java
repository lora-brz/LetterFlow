package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.letterflow.domain.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserLogin(String login);
}
