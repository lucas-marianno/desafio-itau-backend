package com.example.itau_backend.dto.inbound;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
    @NotNull Double valor,
    @NotNull OffsetDateTime dataHora) {
}
