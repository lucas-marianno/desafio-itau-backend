package com.example.itau_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.stream.Stream;

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

  @Test
  void shouldFindAllTransactions() {

    assertThat(repo.findAll()).isNullOrEmpty();

    final var t1 = Transaction.builder().dataHora(OffsetDateTime.now()).valor(12345.6).build();
    final var t2 = Transaction.builder().dataHora(OffsetDateTime.now()).valor(5432.1).build();

    final var saved1 = repo.save(t1);
    final var saved2 = repo.save(t2);

    final var all = repo.findAll().toList();

    assertThat(all).hasSize(2);
    assertThat(all.get(0)).isEqualTo(saved1);
    assertThat(all.get(1)).isEqualTo(saved2);
    assertThat(all.get(0).dataHora()).isEqualTo(t1.dataHora());
    assertThat(all.get(1).dataHora()).isEqualTo(t2.dataHora());
    assertThat(all.get(0).valor()).isEqualTo(t1.valor());
    assertThat(all.get(1).valor()).isEqualTo(t2.valor());
  }

  Stream<Transaction> provideValidTransaction() {
    final var rnd = new Random();
    return Stream.of(
        Transaction.builder().dataHora(OffsetDateTime.now()).valor(rnd.nextDouble(0, 1_000_000)).build(),
        Transaction.builder().dataHora(OffsetDateTime.now()).valor(rnd.nextDouble(0, 1_000_000)).build(),
        Transaction.builder().dataHora(OffsetDateTime.now()).valor(rnd.nextDouble(0, 1_000_000)).build(),
        Transaction.builder().dataHora(OffsetDateTime.now()).valor(rnd.nextDouble(0, 1_000_000)).build(),
        Transaction.builder().dataHora(OffsetDateTime.now()).valor(rnd.nextDouble(0, 1_000_000)).build());
  }
}
