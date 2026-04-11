package com.wallet.application;

import com.wallet.application.usecase.TransferFundsUseCase;
import com.wallet.domain.entity.Account;
import com.wallet.domain.entity.Transaction;
import com.wallet.domain.entity.User;
import com.wallet.domain.exception.AccountNotFoundException;
import com.wallet.domain.exception.InsufficientFundsException;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.TransactionRepository;
import com.wallet.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferFundsUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferFundsUseCase transferFundsUseCase;

    private Account sourceAccount;
    private Account destinationAccount;
    private User sourceUser;
    private static final String PASSWORD = "test123";

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        sourceUser = new User("Test User", "test@email.com", PASSWORD);
        sourceAccount = new Account(userId);
        sourceAccount.credit(new BigDecimal("500.00"));

        destinationAccount = new Account(UUID.randomUUID());
    }

    @Test
    @DisplayName("Successful transfer debits source and credits destination")
    void successfulTransfer() {
        when(accountRepository.findById(sourceAccount.getId())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(destinationAccount.getId())).thenReturn(Optional.of(destinationAccount));
        when(userRepository.findById(sourceAccount.getUserId())).thenReturn(Optional.of(sourceUser));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        transferFundsUseCase.execute(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("200.00"), PASSWORD);

        assertEquals(new BigDecimal("300.00"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("200.00"), destinationAccount.getBalance());
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Boundary: transfer exact balance succeeds")
    void transferExactBalanceSucceeds() {
        when(accountRepository.findById(sourceAccount.getId())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(destinationAccount.getId())).thenReturn(Optional.of(destinationAccount));
        when(userRepository.findById(sourceAccount.getUserId())).thenReturn(Optional.of(sourceUser));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        transferFundsUseCase.execute(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("500.00"), PASSWORD);

        assertEquals(0, sourceAccount.getBalance().compareTo(BigDecimal.ZERO));
        assertEquals(new BigDecimal("500.00"), destinationAccount.getBalance());
    }

    @Test
    @DisplayName("Boundary: transfer $0.01 over balance throws InsufficientFundsException")
    void transferOverBalanceThrows() {
        when(accountRepository.findById(sourceAccount.getId())).thenReturn(Optional.of(sourceAccount));
        when(userRepository.findById(sourceAccount.getUserId())).thenReturn(Optional.of(sourceUser));
        when(accountRepository.findById(destinationAccount.getId())).thenReturn(Optional.of(destinationAccount));

        assertThrows(InsufficientFundsException.class, () ->
                transferFundsUseCase.execute(sourceAccount.getId(), destinationAccount.getId(), new BigDecimal("500.01"), PASSWORD)
        );

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Transfer to same account throws IllegalArgumentException")
    void transferToSameAccountThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                transferFundsUseCase.execute(sourceAccount.getId(), sourceAccount.getId(), new BigDecimal("100.00"), PASSWORD)
        );

        verifyNoInteractions(accountRepository);
    }

    @Test
    @DisplayName("Transfer from non-existent account throws AccountNotFoundException")
    void transferFromNonExistentAccountThrows() {
        UUID unknownId = UUID.randomUUID();
        when(accountRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () ->
                transferFundsUseCase.execute(unknownId, destinationAccount.getId(), new BigDecimal("100.00"), PASSWORD)
        );
    }

    @Test
    @DisplayName("Transfer to non-existent destination throws AccountNotFoundException")
    void transferToNonExistentDestinationThrows() {
        UUID unknownId = UUID.randomUUID();
        when(accountRepository.findById(sourceAccount.getId())).thenReturn(Optional.of(sourceAccount));
        when(userRepository.findById(sourceAccount.getUserId())).thenReturn(Optional.of(sourceUser));
        when(accountRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () ->
                transferFundsUseCase.execute(sourceAccount.getId(), unknownId, new BigDecimal("100.00"), PASSWORD)
        );
    }
}
