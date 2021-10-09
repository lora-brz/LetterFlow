package ru.example.letterflow.security;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.letterflow.domain.entity.User;
import ru.example.letterflow.security.jwt.JwtUser;
import ru.example.letterflow.security.jwt.JwtUserFactory;
import ru.example.letterflow.service.UserService;
import ru.example.letterflow.service.mapping.UserMapper;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = UserMapper.USER_MAPPER.toEntity(userService.findUserByLogin(login));
        if(user == null){
            throw new UsernameNotFoundException("Пользователь с логином " + login + " не найден");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
