package com.example.itau_backend.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.itau_backend.TestFactory;

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
    final var t = TestFactory.provideValidTransaction();
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

    final var t1 = TestFactory.provideValidTransaction();
    final var t2 = TestFactory.provideValidTransaction();

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

  @Test
  void shouldDeleteATransaction() {
    final var t1 = TestFactory.provideValidTransaction();
    final var t2 = TestFactory.provideValidTransaction();

    final var saved1 = repo.save(t1);
    final var saved2 = repo.save(t2);

    repo.delete(saved1.id());

    final var allT = repo.findAll().toList();

    assertThat(allT).hasSize(1);
    assertThat(allT.getFirst()).isEqualTo(saved2);
  }

  @Test
  void shouldDeleteAllTransactions() {
    final var expectedLen = 30;

    final var transactions = TestFactory.provideValidTransactions(expectedLen);
    transactions.forEach(repo::save);

    final var memBefore = repo.findAll().toList();
    assertThat(memBefore).hasSize(expectedLen);

    repo.deleteAll();

    final var memAfter = repo.findAll().toList();
    assertThat(memAfter).hasSize(0);
  }
}
