package com.example.itau_backend.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.itau_backend.dto.inbound.TransactionRequest;
import com.example.itau_backend.dto.outbound.TransactionStatisticsResponse;
import com.example.itau_backend.exception.exceptions.IllegalTransactionException;
import com.example.itau_backend.model.Transaction;
import com.example.itau_backend.repository.TransactionRepository;
import com.example.itau_backend.util.BigDecimalSummaryStatistics;

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

  public TransactionStatisticsResponse getStatistics(int lastSeconds) {
    final var recentTransactions = getMostRecent(lastSeconds);
    final var stats = new BigDecimalSummaryStatistics(recentTransactions.map(Transaction::valor));

    return TransactionStatisticsResponse.fromBigDecimalSummaryStatistics(stats);
  }

  public TransactionStatisticsResponse getStatistics() {
    return getStatistics(60);
  }

  private Stream<Transaction> getMostRecent(int lastSeconds) {
    final var s = OffsetDateTime.now().minusSeconds(lastSeconds);

    return repo
        .findAll()
        .filter(t -> t.dataHora().isAfter(s));
  }

  public List<Transaction> findAll() {
    return repo.findAll().toList();
  }
}
