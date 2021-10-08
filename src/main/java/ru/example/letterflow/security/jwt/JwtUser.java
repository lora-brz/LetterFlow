package ru.example.letterflow.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.example.letterflow.domain.entity.Enum.Status;
import ru.example.letterflow.domain.entity.User;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final String login;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
//    private final Date lastPasswordResetDate;

    @Autowired
    public JwtUser(String login, String password,Collection<? extends GrantedAuthority> authorities, boolean enabled) {
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static UserDetails fromUser(User user){
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}
