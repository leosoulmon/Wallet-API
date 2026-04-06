package com.wallet.domain.repository;

import com.wallet.domain.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(UUID id);
    Optional<Account> findByUserId(UUID userId);
}
