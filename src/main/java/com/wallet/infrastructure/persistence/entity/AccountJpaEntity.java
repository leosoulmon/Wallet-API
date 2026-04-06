package com.wallet.infrastructure.persistence.entity;

import com.wallet.domain.entity.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountJpaEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, columnDefinition = "UUID")
    private UUID userId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public AccountJpaEntity() {}

    public static AccountJpaEntity fromDomain(Account account) {
        AccountJpaEntity entity = new AccountJpaEntity();
        entity.id = account.getId();
        entity.userId = account.getUserId();
        entity.balance = account.getBalance();
        entity.createdAt = account.getCreatedAt();
        return entity;
    }

    public Account toDomain() {
        return new Account(id, userId, balance, createdAt);
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public BigDecimal getBalance() { return balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
