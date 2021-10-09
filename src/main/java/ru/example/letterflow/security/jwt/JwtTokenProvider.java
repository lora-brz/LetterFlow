package ru.example.letterflow.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.example.letterflow.domain.entity.Enum.Role;
import ru.example.letterflow.exceptions.JwtAuthenticationException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Autowired
    @Value("letterbox")
    private String secret;
    @Autowired
    @Value("3600000")
    private final Long validityInMillisec;
    @Autowired
    @Value("Authorization")
    private final String autorizationHeader;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService, Long validityInMillisec, String autorizationHeader) {
        this.userDetailsService = userDetailsService;
        this.validityInMillisec = validityInMillisec;
        this.autorizationHeader = autorizationHeader;
    }

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String login, Role role){
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", getRole(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMillisec);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserLogin(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer_")){
//            int l = bearerToken.length();
//            return bearerToken.substring(7, l);
//        }
        return request.getHeader(autorizationHeader);
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException ex){
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }
    private List<String> getRole(Role role){
        List<String> roles = new ArrayList<>();
        roles.add(role.name());
        return roles;
    }
}
