package ru.example.letterflow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ImpossibleActionException extends Exception{
    public ImpossibleActionException(String message) {
        super(message);
    }
}
