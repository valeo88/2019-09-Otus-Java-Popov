package ru.otus.hw13.api.repository;

public class UserRepositoryException extends RuntimeException {
  public UserRepositoryException(Exception ex) {
    super(ex);
  }
}
