package com.wallet.infrastructure.web.dto;

import com.wallet.domain.entity.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        UUID userId,
        BigDecimal balance,
        LocalDateTime createdAt
) {
    public static AccountResponse fromDomain(Account account) {
        return new AccountResponse(account.getId(), account.getUserId(), account.getBalance(), account.getCreatedAt());
    }
}
