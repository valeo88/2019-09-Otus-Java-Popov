package ru.otus.hw12.api.dao;


import ru.otus.hw12.api.model.User;
import ru.otus.hw12.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
