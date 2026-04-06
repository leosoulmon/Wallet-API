package com.wallet.application.usecase;

import com.wallet.domain.entity.Account;
import com.wallet.domain.exception.AccountNotFoundException;
import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetUserAccountUseCase {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public GetUserAccountUseCase(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Account execute(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new AccountNotFoundException(userId));
    }
}
