package ru.example.letterflow.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.example.letterflow.domain.entity.Enum.Permission;
import ru.example.letterflow.domain.entity.Enum.Status;
import ru.example.letterflow.domain.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getUserId(),
                user.getLogin(),
                user.getPassword(),
                user.getPermission(),
                user.getRooms(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthorities(user.getPermission())
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(Permission permission){
        List<Permission> userRoles = new ArrayList<>();
        userRoles.add(permission);
        return userRoles.stream()
                .map(perm-> new SimpleGrantedAuthority(perm.name()))
                .collect(Collectors.toList());
    }
}
