package ru.otus.hw10.api.service;

public class UserServiceException extends RuntimeException {
  public UserServiceException(Exception e) {
    super(e);
  }
}
