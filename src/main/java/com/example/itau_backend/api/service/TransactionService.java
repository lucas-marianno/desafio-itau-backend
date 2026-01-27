package com.example.itau_backend.api.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.example.itau_backend.api.dto.TransactionRequest;
import com.example.itau_backend.api.exception.exceptions.IllegalTransactionException;
import com.example.itau_backend.api.model.Transaction;
import com.example.itau_backend.api.repository.TransactionRepository;

@Service
public class TransactionService {

  private final TransactionRepository repo;

  TransactionService(TransactionRepository repo) {
    this.repo = repo;
  }

  public void save(TransactionRequest request) {
    if (request.valor().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalTransactionException();
    }
    if (request.dataHora().isAfter(OffsetDateTime.now())) {
      throw new IllegalTransactionException();
    }
    repo.save(Transaction.builder()
        .valor(request.valor())
        .dataHora(request.dataHora())
        .build());
  }

  public void deleteAll() {
    repo.deleteAll();
  }
}
