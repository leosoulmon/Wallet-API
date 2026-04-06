package com.wallet.application.usecase;

import com.wallet.domain.entity.Account;
import com.wallet.domain.exception.AccountNotFoundException;
import com.wallet.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetBalanceUseCase {

    private final AccountRepository accountRepository;

    public GetBalanceUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Account execute(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
