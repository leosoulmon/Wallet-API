package com.wallet.infrastructure.web.controller;

import com.wallet.application.usecase.DepositFundsUseCase;
import com.wallet.application.usecase.GetBalanceUseCase;
import com.wallet.application.usecase.TransferFundsUseCase;
import com.wallet.infrastructure.web.dto.AccountResponse;
import com.wallet.infrastructure.web.dto.DepositRequest;
import com.wallet.infrastructure.web.dto.TransferRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
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
    public ResponseEntity<AccountResponse> deposit(@PathVariable UUID accountId,
                                                   @Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(AccountResponse.fromDomain(
                depositFundsUseCase.execute(accountId, request.amount())
        ));
    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable UUID accountId,
                                         @Valid @RequestBody TransferRequest request) {
        transferFundsUseCase.execute(accountId, request.destinationAccountId(), request.amount());
        return ResponseEntity.noContent().build();
    }
}
