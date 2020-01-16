package ru.otus.hw13.web.service;

import org.springframework.stereotype.Service;
import ru.otus.hw13.api.repository.UserRepository;
import ru.otus.hw13.api.sessionmanager.SessionManager;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    public UserAuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String login, String password) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            return userRepository.findByLogin(login)
                .map(user -> user.getPassword().equals(password) && user.getIsAdmin())
                .orElse(false);
        }
    }

}
