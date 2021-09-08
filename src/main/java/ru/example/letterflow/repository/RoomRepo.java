package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.letterflow.domain.entity.Room;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
