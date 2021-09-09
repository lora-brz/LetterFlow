package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.letterflow.domain.entity.Room;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
}
