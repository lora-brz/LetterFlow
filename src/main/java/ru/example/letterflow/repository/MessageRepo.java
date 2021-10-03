package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.letterflow.domain.entity.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    Message findMessagesByRoomName(String roomName);
}
