package com.wallet.infrastructure.persistence.adapter;

import com.wallet.domain.entity.Account;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.infrastructure.persistence.entity.AccountJpaEntity;
import com.wallet.infrastructure.persistence.jpa.AccountJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountJpaRepository jpaRepository;

    public AccountRepositoryAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account save(Account account) {
        AccountJpaEntity entity = AccountJpaEntity.fromDomain(account);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaRepository.findById(id).map(AccountJpaEntity::toDomain);
    }

    @Override
    public Optional<Account> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(AccountJpaEntity::toDomain);
    }
}
