package com.example.itau_backend.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.example.itau_backend.dto.inbound.TransactionRequest;
import com.example.itau_backend.exception.exceptions.IllegalTransactionException;
import com.example.itau_backend.model.Transaction;
import com.example.itau_backend.repository.TransactionRepository;

@Service
public class TransactionService {

  private final TransactionRepository repo;

  TransactionService(TransactionRepository repo) {
    this.repo = repo;
  }

  public void save(TransactionRequest request) {
    if (request.valor() < 0) {
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
