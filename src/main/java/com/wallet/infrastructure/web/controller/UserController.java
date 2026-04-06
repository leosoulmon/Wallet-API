package com.wallet.infrastructure.web.controller;

import com.wallet.application.usecase.CreateUserUseCase;
import com.wallet.domain.entity.User;
import com.wallet.domain.repository.AccountRepository;
import com.wallet.domain.repository.UserRepository;
import com.wallet.infrastructure.web.dto.AccountResponse;
import com.wallet.infrastructure.web.dto.CreateUserRequest;
import com.wallet.infrastructure.web.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserController(CreateUserUseCase createUserUseCase,
                          UserRepository userRepository,
                          AccountRepository accountRepository) {
        this.createUserUseCase = createUserUseCase;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = createUserUseCase.execute(request.name(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromDomain(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(UserResponse.fromDomain(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/account")
    public ResponseEntity<AccountResponse> getUserAccount(@PathVariable UUID userId) {
        return accountRepository.findByUserId(userId)
                .map(account -> ResponseEntity.ok(AccountResponse.fromDomain(account)))
                .orElse(ResponseEntity.notFound().build());
    }
}
