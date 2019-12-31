package ru.otus.hw12.api.dao;


import ru.otus.hw12.api.model.User;
import ru.otus.hw12.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
  List<User> getAll();

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  long saveUser(User user);

  SessionManager getSessionManager();
}
