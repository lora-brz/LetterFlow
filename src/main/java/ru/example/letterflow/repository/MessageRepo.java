package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.letterflow.domain.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
