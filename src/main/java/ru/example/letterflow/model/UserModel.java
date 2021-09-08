package ru.example.letterflow.model;

import ru.example.letterflow.domain.entity.User;

public class UserModel {
    private Long userId;
    private String login;

    public static UserModel toModel(User user){
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getUserId());
        userModel.setLogin(user.getLogin());
        return userModel;
    }

    public UserModel() {
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
}
