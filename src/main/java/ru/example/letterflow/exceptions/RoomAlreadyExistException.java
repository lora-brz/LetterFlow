package ru.example.letterflow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RoomAlreadyExistException extends Exception{

    public RoomAlreadyExistException(String message) {
        super(message);
    }
}
