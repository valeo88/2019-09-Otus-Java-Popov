package ru.otus.hw11.api.service;


import ru.otus.hw11.api.model.User;

import java.util.Optional;

public interface UserService {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
