package ru.example.letterflow.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.example.letterflow.domain.entity.Enum.Permission;
import ru.example.letterflow.domain.entity.Enum.Status;
import ru.example.letterflow.domain.entity.Room;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtUser implements UserDetails {

    private final Long userId;
    private final String login;
    private final String password;
    private final Permission permission;
//    private final Date created;
//    private final Date updated;
//    private final Status status;
    private final List<Room> rooms;
    private final boolean enabled;
    private final Date lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long userId, String login, String password, Permission permission, List<Room> rooms, boolean enabled,
                   Date lastPasswordResetDate, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.permission = permission;
        this.rooms = rooms;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public Permission getPermission() {
        return permission;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
