package ru.otus.hw09.api.service;


import ru.otus.hw09.api.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

  long saveAccount(Account account);

  Optional<Account> getAccount(long no);

}
