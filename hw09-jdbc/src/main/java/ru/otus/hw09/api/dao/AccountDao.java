package ru.otus.hw09.api.dao;

import ru.otus.hw09.api.model.Account;
import ru.otus.hw09.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
  Optional<Account> findByNo(long no);

  long saveAccount(Account account);

  SessionManager getSessionManager();
}
