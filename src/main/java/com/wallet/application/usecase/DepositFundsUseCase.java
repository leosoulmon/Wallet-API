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
public class DepositFundsUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public DepositFundsUseCase(AccountRepository accountRepository,
                               TransactionRepository transactionRepository,
                               UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account execute(UUID accountId, BigDecimal amount, String password) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        User user = userRepository.findById(account.getUserId())
                .orElseThrow(() -> new UserNotFoundException(account.getUserId()));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        account.credit(amount);
        Account saved = accountRepository.save(account);

        Transaction transaction = new Transaction(
                accountId,
                Transaction.Type.DEPOSIT,
                amount,
                "Deposit of " + amount
        );
        transactionRepository.save(transaction);

        return saved;
    }
}
