package com.wallet.infrastructure.persistence.jpa;

import com.wallet.infrastructure.persistence.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, UUID> {
    List<TransactionJpaEntity> findByAccountIdOrderByCreatedAtDesc(UUID accountId);
}
