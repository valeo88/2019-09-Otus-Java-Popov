package ru.otus.hw15.web.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
