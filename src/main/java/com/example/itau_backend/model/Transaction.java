package com.example.itau_backend.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;

@Builder
public record Transaction(
    Long id,
    BigDecimal valor,
    OffsetDateTime dataHora) {
}
