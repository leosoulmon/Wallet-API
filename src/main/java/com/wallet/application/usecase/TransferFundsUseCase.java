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
public class TransferFundsUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferFundsUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account execute(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("Source and destination accounts must be different");
        }

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountId));

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
