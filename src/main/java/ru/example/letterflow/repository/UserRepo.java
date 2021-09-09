package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.letterflow.domain.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserLogin(String login);
}
