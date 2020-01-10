package ru.otus.hw13.api.service;

public class UserServiceException extends RuntimeException {
  public UserServiceException(Exception e) {
    super(e);
  }
}
