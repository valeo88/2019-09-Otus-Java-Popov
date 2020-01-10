package ru.otus.hw13.api.repository;


import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  List<User> getAll();

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  long saveUser(User user);

  SessionManager getSessionManager();
}
