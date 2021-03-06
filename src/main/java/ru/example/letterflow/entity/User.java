package ru.example.letterflow.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "character")
    private String character;

//    продумать для Room и Message:
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
//    private ArrayList<Room> rooms;
//
//    public ArrayList<Room> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(ArrayList<Room> rooms) {
//        this.rooms = rooms;
//    }


    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

}
