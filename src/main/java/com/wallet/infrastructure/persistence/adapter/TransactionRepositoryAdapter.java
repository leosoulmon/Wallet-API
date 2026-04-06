package com.wallet.infrastructure.persistence.adapter;

import com.wallet.domain.entity.Transaction;
import com.wallet.domain.repository.TransactionRepository;
import com.wallet.infrastructure.persistence.entity.TransactionJpaEntity;
import com.wallet.infrastructure.persistence.jpa.TransactionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionJpaEntity entity = TransactionJpaEntity.fromDomain(transaction);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return jpaRepository.findByAccountIdOrderByCreatedAtDesc(accountId)
                .stream()
                .map(TransactionJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
