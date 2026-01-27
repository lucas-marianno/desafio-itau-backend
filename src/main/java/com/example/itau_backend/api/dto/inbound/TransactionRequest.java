package com.example.itau_backend.api.dto.inbound;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
    @NotNull BigDecimal valor,
    @NotNull OffsetDateTime dataHora) {
}
