package ru.otus.hw15.api.service;

public class UserServiceException extends RuntimeException {
  public UserServiceException(Exception e) {
    super(e);
  }
}
