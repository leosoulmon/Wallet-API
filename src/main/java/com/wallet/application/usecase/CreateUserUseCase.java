package com.wallet.application.usecase;

import com.wallet.domain.entity.Account;
import com.wallet.domain.entity.User;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public CreateUserUseCase(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public User execute(String name, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use: " + email);
        }
        User user = new User(name, email);
        User saved = userRepository.save(user);
        Account account = new Account(saved.getId());
        accountRepository.save(account);
        return saved;
    }
}
