package com.wallet.infrastructure.web.dto;

public record ApiResponse<T>(
        String message,
        T data
) {
}
