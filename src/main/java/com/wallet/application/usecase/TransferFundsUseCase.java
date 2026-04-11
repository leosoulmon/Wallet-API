package com.wallet.application.usecase;

import com.wallet.domain.entity.Account;
import com.wallet.domain.entity.Transaction;
import com.wallet.domain.entity.User;
import com.wallet.domain.exception.AccountNotFoundException;
import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.TransactionRepository;
import com.wallet.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferFundsUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransferFundsUseCase(AccountRepository accountRepository,
                                TransactionRepository transactionRepository,
                                UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account execute(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount, String password) {
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("Source and destination accounts must be different");
        }

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountId));

        User user = userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException(source.getUserId()));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        Account destination = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new AccountNotFoundException(destinationAccountId));

        source.debit(amount);
        destination.credit(amount);

        accountRepository.save(source);
        accountRepository.save(destination);

        transactionRepository.save(new Transaction(
                sourceAccountId,
                Transaction.Type.TRANSFER_DEBIT,
                amount,
                "Transfer to account " + destinationAccountId
        ));
        transactionRepository.save(new Transaction(
                destinationAccountId,
                Transaction.Type.TRANSFER_CREDIT,
                amount,
                "Transfer from account " + sourceAccountId
        ));

        return source;
    }
}
