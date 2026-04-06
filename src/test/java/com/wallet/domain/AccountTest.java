package com.wallet.domain;

import com.wallet.domain.entity.Account;
import com.wallet.domain.exception.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(UUID.randomUUID());
    }

    @Test
    @DisplayName("New account should have zero balance")
    void newAccountHasZeroBalance() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    @DisplayName("Credit should increase balance")
    void creditIncreasesBalance() {
        account.credit(new BigDecimal("100.00"));
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    @DisplayName("Debit should decrease balance")
    void debitDecreasesBalance() {
        account.credit(new BigDecimal("200.00"));
        account.debit(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    @Test
    @DisplayName("Debit exact balance should result in zero")
    void debitExactBalanceResultsInZero() {
        account.credit(new BigDecimal("100.00"));
        account.debit(new BigDecimal("100.00"));
        assertEquals(0, account.getBalance().compareTo(BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Debit more than balance throws InsufficientFundsException")
    void debitMoreThanBalanceThrowsException() {
        account.credit(new BigDecimal("100.00"));
        assertThrows(InsufficientFundsException.class,
                () -> account.debit(new BigDecimal("100.01")));
    }

    @Test
    @DisplayName("Boundary: debit $0.01 more than balance throws InsufficientFundsException")
    void boundaryDebitOnecentOverBalanceThrows() {
        account.credit(new BigDecimal("50.00"));
        assertThrows(InsufficientFundsException.class,
                () -> account.debit(new BigDecimal("50.01")));
    }

    @Test
    @DisplayName("Credit with zero or negative amount throws IllegalArgumentException")
    void creditZeroOrNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.credit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.credit(new BigDecimal("-1")));
    }

    @Test
    @DisplayName("Debit with zero or negative amount throws IllegalArgumentException")
    void debitZeroOrNegativeThrows() {
        account.credit(new BigDecimal("100.00"));
        assertThrows(IllegalArgumentException.class, () -> account.debit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.debit(new BigDecimal("-5")));
    }

    @Test
    @DisplayName("Credit null amount throws IllegalArgumentException")
    void creditNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.credit(null));
    }

    @Test
    @DisplayName("Debit on empty account throws InsufficientFundsException")
    void debitOnEmptyAccountThrows() {
        assertThrows(InsufficientFundsException.class, () -> account.debit(new BigDecimal("0.01")));
    }
}
