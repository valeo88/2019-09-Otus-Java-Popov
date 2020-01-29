package ru.otus.hw15.api.repository;


import ru.otus.hw15.api.model.User;
import ru.otus.hw15.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  List<User> getAll();

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  long saveUser(User user);

  SessionManager getSessionManager();
}
