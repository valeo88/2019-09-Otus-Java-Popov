package ru.otus.hw09.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.api.dao.AccountDao;
import ru.otus.hw09.api.model.Account;
import ru.otus.hw09.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceAccount {
  private static Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

  private final AccountDao accountDao;

  public DbServiceAccountImpl(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public long saveAccount(Account account) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long accountId = accountDao.saveAccount(account);
        sessionManager.commitSession();

        logger.info("saved account: {}", accountId);
        return accountId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }


  @Override
  public Optional<Account> getAccount(long no) {
    try (SessionManager sessionManager = accountDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<Account> accountOptional = accountDao.findByNo(no);

        logger.info("loaded account: {}", accountOptional.orElse(null));
        return accountOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
