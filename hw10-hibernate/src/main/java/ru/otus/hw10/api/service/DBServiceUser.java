package ru.otus.hw10.api.service;


import ru.otus.hw10.api.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
