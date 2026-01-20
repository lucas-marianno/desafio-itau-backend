package com.example.itau_backend.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.itau_backend.model.Transaction;

@Repository
public class TransactionRepository {
  private Map<Long, Transaction> memory = new ConcurrentHashMap<Long, Transaction>();

  private AtomicLong idGenerator = new AtomicLong(1);

  public Transaction save(Transaction t) {
    if (t.id() != null) {
      memory.put(t.id(), t);
      return t;
    }

    long id = idGenerator.incrementAndGet();
    Transaction newT = Transaction.builder()
        .id(id)
        .valor(t.valor())
        .dataHora(t.dataHora())
        .build();

    memory.put(id, newT);
    return newT;
  }

  public Optional<Transaction> findById(long id) {
    final var found = memory.get(id);

    return found == null ? Optional.empty() : Optional.of(found);
  }
}
