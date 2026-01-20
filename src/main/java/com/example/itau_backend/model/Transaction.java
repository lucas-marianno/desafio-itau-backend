package com.example.itau_backend.model;

import java.time.OffsetDateTime;

import lombok.Builder;

@Builder
public record Transaction(
    Long id,
    Double valor,
    OffsetDateTime dataHora) {
}
