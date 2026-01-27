package com.example.itau_backend.statistics.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.itau_backend.api.model.Transaction;
import com.example.itau_backend.api.repository.TransactionRepository;
import com.example.itau_backend.common.util.BigDecimalSummaryStatistics;
import com.example.itau_backend.statistics.dto.TransactionStatisticsResponse;

@Service
public class StatisticsService {

  private final TransactionRepository repo;

  StatisticsService(TransactionRepository repo) {
    this.repo = repo;
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
