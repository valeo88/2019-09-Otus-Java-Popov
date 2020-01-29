package ru.otus.hw15.api.service;


import ru.otus.hw15.api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  long saveUser(User user);

  Optional<User> getUser(long id);

  Optional<User> getUser(String login);

  List<User> getAll();

}
