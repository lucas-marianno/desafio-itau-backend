package com.example.itau_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.itau_backend.model.Transaction;

@DisplayName("Testing TransactionRepository")
public class TransactionRepositoryTest {

  TransactionRepositoryTest() {
    this.repo = new TransactionRepository();
  }

  final TransactionRepository repo;

  @Test
  void shouldNotFindTransaction() {
    final long id = 1;

    final var found = repo.findById(id);
    assertThat(found).isNotPresent();
  }

  @Test
  void shouldCreateAndFindTransaction() {
    final var t = Transaction.builder()
        .dataHora(OffsetDateTime.now())
        .valor(12345.6)
        .build();

    final var saved = repo.save(t);

    assertThat(saved.id()).isNotNull();

    final var found = repo.findById(saved.id());

    assertThat(found).isPresent();
    assertThat(found.get()).isEqualTo(saved);
    assertThat(found.get().dataHora()).isEqualTo(t.dataHora());
    assertThat(found.get().valor()).isEqualTo(t.valor());
  }

}
