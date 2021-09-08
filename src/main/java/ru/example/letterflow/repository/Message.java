package ru.example.letterflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Message extends JpaRepository<Message, Long> {
}
