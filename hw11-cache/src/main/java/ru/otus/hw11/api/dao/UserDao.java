package ru.otus.hw11.api.dao;


import ru.otus.hw11.api.model.User;

import java.util.Optional;

public interface UserDao extends AutoCloseable {
  Optional<User> findById(long id);

  long saveUser(User user);
}
