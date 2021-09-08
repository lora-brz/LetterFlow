package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.letterflow.domain.entity.Message;

public interface RoomRepo extends JpaRepository<Message, Long> {
}
