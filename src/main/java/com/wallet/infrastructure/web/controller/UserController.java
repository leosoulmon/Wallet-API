package com.wallet.infrastructure.web.controller;

import com.wallet.application.usecase.CreateUserUseCase;
import com.wallet.application.usecase.GetUserAccountUseCase;
import com.wallet.application.usecase.GetUserUseCase;
import com.wallet.infrastructure.web.dto.AccountResponse;
import com.wallet.infrastructure.web.dto.ApiResponse;
import com.wallet.infrastructure.web.dto.CreateUserRequest;
import com.wallet.infrastructure.web.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserAccountUseCase getUserAccountUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserUseCase getUserUseCase,
                          GetUserAccountUseCase getUserAccountUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.getUserAccountUseCase = getUserAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = UserResponse.fromDomain(createUserUseCase.execute(request.name(), request.email()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("user created successfully", user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(UserResponse.fromDomain(getUserUseCase.execute(userId)));
    }

    @GetMapping("/{userId}/account")
    public ResponseEntity<AccountResponse> getUserAccount(@PathVariable UUID userId) {
        return ResponseEntity.ok(AccountResponse.fromDomain(getUserAccountUseCase.execute(userId)));
    }
}
