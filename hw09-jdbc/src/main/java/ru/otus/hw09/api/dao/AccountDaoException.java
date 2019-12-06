package ru.otus.hw09.api.dao;

public class AccountDaoException extends RuntimeException {
  public AccountDaoException(Exception ex) {
    super(ex);
  }
}
