package ru.example.letterflow.security;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.security.jwt.JwtUser;
import ru.example.letterflow.security.jwt.JwtUserFactory;
import ru.example.letterflow.service.UserService;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto userDto = userService.findUserByLogin(login);
        if(userDto == null){
            throw new UsernameNotFoundException("Пользователь с логином " + login + " не найден");
        }
        JwtUser jwtUser = JwtUserFactory.create(userDto);
        return jwtUser;
    }
}
