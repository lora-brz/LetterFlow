package ru.example.letterflow.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "login")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "character")
    @Enumerated(EnumType.STRING)
    private Character character;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
    private List<Room> rooms;
//
//    public List<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(List<Room> rooms) {
//        this.rooms = rooms;
//    }

    public User() {
    }
}
