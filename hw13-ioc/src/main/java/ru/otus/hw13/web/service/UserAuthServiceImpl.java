package ru.otus.hw13.web.service;

import org.springframework.stereotype.Service;
import ru.otus.hw13.api.repository.UserRepository;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    public UserAuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return true;
//        try (SessionManager sessionManager = userRepository.getSessionManager()) {
//            sessionManager.beginSession();
//            boolean isValid =  userRepository.findByLogin(login)
//                .map(user -> user.getPassword().equals(password) && user.getIsAdmin())
//                .orElse(false);
//            if (isValid) {
//                userSessions.add(userSessionId);
//            }
//            return isValid;
//
//        }
    }

}
