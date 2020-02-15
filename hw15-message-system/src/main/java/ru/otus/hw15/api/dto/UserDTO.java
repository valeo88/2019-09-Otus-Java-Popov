package ru.otus.hw15.api.dto;

import ru.otus.hw15.api.model.User;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private long id;
    private String name;
    private String login;
    private String password;
    private boolean isAdmin;

    public UserDTO() {
    }

    public UserDTO(User user) {
        if (user!=null) {
            this.id = user.getId();
            this.name = user.getName();
            this.login = user.getLogin();
            this.isAdmin = user.getIsAdmin();
            this.password = "******";
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
