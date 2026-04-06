package com.wallet.application.usecase;

import com.wallet.domain.entity.Account;
import com.wallet.domain.entity.Transaction;
import com.wallet.domain.exception.AccountNotFoundException;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class DepositFundsUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public DepositFundsUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account execute(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

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
