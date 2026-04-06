package com.wallet.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    public enum Type {
        DEPOSIT, TRANSFER_DEBIT, TRANSFER_CREDIT
    }

    private final UUID id;
    private final UUID accountId;
    private final Type type;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime createdAt;

    public Transaction(UUID accountId, Type type, BigDecimal amount, String description) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Transaction(UUID id, UUID accountId, Type type, BigDecimal amount, String description, LocalDateTime createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getAccountId() { return accountId; }
    public Type getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
