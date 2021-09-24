package ru.example.letterflow.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    public Object userAlreadyExistException(UserAlreadyExistException ex, WebRequest request){
        return response (HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Object userNotFoundException(UserNotFoundException ex, WebRequest request){
        return response (HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(RoomAlreadyExistException.class)
    public  Object roomAlreadyExistException(RoomAlreadyExistException ex, WebRequest request){
        return response (HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(RoomAlreadyExistException.class)
    public  Object insufficientAccessRightsException(InsufficientAccessRightsException ex, WebRequest request){
        return response (HttpStatus.FORBIDDEN, ex, request);
    }

    public Object response(HttpStatus status, Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();

        body.put("status", status.toString());
        body.put("message", ex.getMessage());
        body.put("exception", ex.getClass().getName());
        body.put("path", request.getDescription(false).replaceFirst("uri=", ""));
        return new ResponseEntity<> (body, headers, status);
    }
}
