package ru.example.letterflow.domain.entity.Enum;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.BLOCK_USER, Permission.CHANGE_ROLE, Permission.SEND_MESSAGE, Permission.READ_MESSAGE, Permission.DELETE_MESSAGE)),
    MODERATOR(Set.of(Permission.BLOCK_USER, Permission.SEND_MESSAGE, Permission.READ_MESSAGE, Permission.DELETE_MESSAGE)),
    USER(Set.of(Permission.SEND_MESSAGE, Permission.SEND_MESSAGE, Permission.READ_MESSAGE)),
    BLOCKED(Set.of(Permission.READ_MESSAGE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}


