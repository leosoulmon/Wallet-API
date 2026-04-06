package com.wallet.domain.repository;

import com.wallet.domain.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findByAccountId(UUID accountId);
}
