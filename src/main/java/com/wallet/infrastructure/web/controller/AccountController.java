package com.wallet.infrastructure.web.controller;

import com.wallet.application.usecase.DepositFundsUseCase;
import com.wallet.application.usecase.GetBalanceUseCase;
import com.wallet.application.usecase.TransferFundsUseCase;
import com.wallet.infrastructure.web.dto.AccountResponse;
import com.wallet.infrastructure.web.dto.ApiResponse;
import com.wallet.infrastructure.web.dto.DepositRequest;
import com.wallet.infrastructure.web.dto.TransferRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final GetBalanceUseCase getBalanceUseCase;
    private final DepositFundsUseCase depositFundsUseCase;
    private final TransferFundsUseCase transferFundsUseCase;

    public AccountController(GetBalanceUseCase getBalanceUseCase,
                             DepositFundsUseCase depositFundsUseCase,
                             TransferFundsUseCase transferFundsUseCase) {
        this.getBalanceUseCase = getBalanceUseCase;
        this.depositFundsUseCase = depositFundsUseCase;
        this.transferFundsUseCase = transferFundsUseCase;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<AccountResponse> getBalance(@PathVariable UUID accountId) {
        return ResponseEntity.ok(AccountResponse.fromDomain(getBalanceUseCase.execute(accountId)));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> deposit(@PathVariable UUID accountId,
                                                                @Valid @RequestBody DepositRequest request) {
        AccountResponse account = AccountResponse.fromDomain(
                depositFundsUseCase.execute(accountId, request.amount())
        );
        return ResponseEntity.ok(new ApiResponse<>("value deposited successfully", account));
    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<ApiResponse<AccountResponse>> transfer(@PathVariable UUID accountId,
                                                                  @Valid @RequestBody TransferRequest request) {
        AccountResponse account = AccountResponse.fromDomain(
                transferFundsUseCase.execute(accountId, request.destinationAccountId(), request.amount())
        );
        return ResponseEntity.ok(new ApiResponse<>("value transferred successfully", account));
    }
}
