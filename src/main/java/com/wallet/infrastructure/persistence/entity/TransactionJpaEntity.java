package com.wallet.infrastructure.persistence.entity;

import com.wallet.domain.entity.Transaction;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionJpaEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, columnDefinition = "UUID")
    private UUID accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transaction.Type type;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public TransactionJpaEntity() {}

    public static TransactionJpaEntity fromDomain(Transaction transaction) {
        TransactionJpaEntity entity = new TransactionJpaEntity();
        entity.id = transaction.getId();
        entity.accountId = transaction.getAccountId();
        entity.type = transaction.getType();
        entity.amount = transaction.getAmount();
        entity.description = transaction.getDescription();
        entity.createdAt = transaction.getCreatedAt();
        return entity;
    }

    public Transaction toDomain() {
        return new Transaction(id, accountId, type, amount, description, createdAt);
    }

    public UUID getId() { return id; }
    public UUID getAccountId() { return accountId; }
    public Transaction.Type getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
