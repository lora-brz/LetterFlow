package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.letterflow.domain.entity.Room;
import ru.example.letterflow.domain.entity.User;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    Room findByRoomName (String roomName);
}
