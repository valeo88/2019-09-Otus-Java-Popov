package ru.otus.hw13.web.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
