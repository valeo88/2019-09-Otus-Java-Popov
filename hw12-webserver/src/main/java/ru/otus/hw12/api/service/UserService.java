package ru.otus.hw12.api.service;


import ru.otus.hw12.api.model.User;

import java.util.Optional;

public interface UserService {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
