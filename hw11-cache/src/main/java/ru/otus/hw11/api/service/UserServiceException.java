package ru.otus.hw11.api.service;

public class UserServiceException extends RuntimeException {
  public UserServiceException(Exception e) {
    super(e);
  }
}
