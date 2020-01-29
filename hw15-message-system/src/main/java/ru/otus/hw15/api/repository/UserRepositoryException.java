package ru.otus.hw15.api.repository;

public class UserRepositoryException extends RuntimeException {
  public UserRepositoryException(Exception ex) {
    super(ex);
  }
}
