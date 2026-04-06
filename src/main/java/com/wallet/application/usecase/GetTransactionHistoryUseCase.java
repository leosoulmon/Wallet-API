package com.wallet.application.usecase;

import com.wallet.domain.entity.Transaction;
import com.wallet.domain.exception.AccountNotFoundException;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GetTransactionHistoryUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public GetTransactionHistoryUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<Transaction> execute(UUID accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        return transactionRepository.findByAccountId(accountId);
    }
}
