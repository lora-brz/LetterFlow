package ru.example.letterflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.example.letterflow.domain.dto.AuthenticationRequestDto;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.exceptions.UserAlreadyExistException;
import ru.example.letterflow.exceptions.UserNotFoundException;
import ru.example.letterflow.security.jwt.JwtTokenProvider;
import ru.example.letterflow.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequestDto requestDto) throws UserNotFoundException {
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
        }
    }

    @PostMapping("/logout")
    public void logout (HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }

    @GetMapping("/registration")
    public UserDto registration(UserDto userDto) throws UserAlreadyExistException {
        return userService.registrationUser(userDto);
    }

}
