package ru.example.letterflow.domain.entity.Enum;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of("admin", "moderator", "user", "every")),
    MODERATOR(Set.of("moderator", "user", "every")),
    USER(Set.of("user", "every")),
    BLOCKED(Set.of("every"));

    private final Set<String> permissions;

    Role(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission))
                .collect(Collectors.toSet());
    }
}


