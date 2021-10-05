package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.letterflow.domain.dto.AuthenticationRequestDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.security.jwt.JwtTokenProvider;
import ru.example.letterflow.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) throws UserNotFoundException {
        try{
            String login = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            UserDto userDto = userService.findUserByLogin(login);
            String token = jwtTokenProvider.createToken(login, userDto.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException ex){
            throw new BadCredentialsException("Invalid login or password");
        }catch (UserNotFoundException ex) {
            throw new UserNotFoundException("User not found");
        }
    }
}
