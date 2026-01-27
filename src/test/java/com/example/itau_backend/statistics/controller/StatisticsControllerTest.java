package com.example.itau_backend.statistics.controller;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.itau_backend.TestFactory;
import com.example.itau_backend.api.model.Transaction;
import com.example.itau_backend.api.repository.TransactionRepository;
import com.example.itau_backend.common.util.BigDecimalSummaryStatistics;
import com.example.itau_backend.statistics.dto.TransactionStatisticsResponse;

@SpringBootTest
@AutoConfigureRestTestClient
public class StatisticsControllerTest {

  @Autowired
  RestTestClient rtc;

  @Autowired
  TransactionRepository repo;

  @AfterEach
  public void clearMemAfterEach() {
    repo.deleteAll();
  }

  @Test
  public void getStatisticsShouldReturn200() {
    final String uri = "/estatistica";

    rtc.get()
        .uri(uri)
        .exchange()
        .expectStatus().isOk()
        .expectBody(TransactionStatisticsResponse.class);
  }

  @Test
  public void getStatisticsWithSecondsShouldReturn200() {
    final String uri = "/estatistica?segundos=200";

    rtc.get()
        .uri(uri)
        .exchange()
        .expectStatus().isOk()
        .expectBody(TransactionStatisticsResponse.class);
  }

  @Test
  public void getStatisticsShouldReturnAccurateStats() {
    final var lastSeconds = OffsetDateTime.now().minusSeconds(60);

    // generate and save to repo
    final var transactions = TestFactory
        .provideValidTransactions(10000, 30)
        .peek(repo::save);

    // manually generate statsDto
    final var bigDecimalStream = transactions
        .filter(t -> t.dataHora().isAfter(lastSeconds))
        .map(Transaction::valor);
    final var stats = new BigDecimalSummaryStatistics(bigDecimalStream);
    final var statsDto = TransactionStatisticsResponse.fromBigDecimalSummaryStatistics(stats);

    // compare to api response
    rtc.get()
        .uri("/estatistica")
        .exchange()
        .expectStatus().isOk()
        .expectBody(TransactionStatisticsResponse.class)
        .isEqualTo(statsDto);
  }

}
