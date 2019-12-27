package ru.otus.hw12.web.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
