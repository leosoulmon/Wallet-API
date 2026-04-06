package com.wallet.infrastructure.web.controller;

import com.wallet.application.usecase.GetTransactionHistoryUseCase;
import com.wallet.infrastructure.web.dto.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class TransactionController {

    private final GetTransactionHistoryUseCase getTransactionHistoryUseCase;

    public TransactionController(GetTransactionHistoryUseCase getTransactionHistoryUseCase) {
        this.getTransactionHistoryUseCase = getTransactionHistoryUseCase;
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable UUID accountId) {
        List<TransactionResponse> transactions = getTransactionHistoryUseCase.execute(accountId)
                .stream()
                .map(TransactionResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }
}
