package ru.example.letterflow.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.Enum.Role;
import ru.example.letterflow.domain.entity.Enum.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserDto userDto) {
        return new JwtUser(
                userDto.getUserId(),
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getRole(),
                userDto.getRooms(),
                userDto.getStatus().equals(Status.ACTIVE),
                userDto.getUpdated(),
                mapToGrantedAuthorities(userDto.getRole())
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role){
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);
        return userRoles.stream()
                .map(perm-> new SimpleGrantedAuthority(perm.name()))
                .collect(Collectors.toList());
    }
}
