package com.example.itau_backend.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.itau_backend.TestFactory;
import com.example.itau_backend.api.dto.outbound.TransactionStatisticsResponse;
import com.example.itau_backend.api.model.Transaction;
import com.example.itau_backend.api.repository.TransactionRepository;
import com.example.itau_backend.common.util.BigDecimalSummaryStatistics;

@SpringBootTest
@AutoConfigureRestTestClient
public class TransactionControllerTest {

  @Autowired
  RestTestClient rtc;

  @Autowired
  TransactionRepository repo;

  @AfterEach
  public void clearMemAfterEach() {
    repo.deleteAll();
  }

  // POST TRANSACATION ENDPOINT

  @ParameterizedTest
  @MethodSource("com.example.itau_backend.TestFactory#provideValidTransactionPostBody")
  public void postTransactionShouldReturn201(Map<String, Object> body) {
    final String uri = "/transacao";

    rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .expectStatus().isCreated()
        .expectBody().isEmpty();

    final var mem = repo.findAll().toList();

    assertThat(mem.size()).isGreaterThanOrEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("com.example.itau_backend.TestFactory#provideIllegalTransactionPostBody")
  public void postTransactionWithIllegalBodyShouldReturn422(Map<String, Object> body) {
    final String uri = "/transacao";

    var result = rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .returnResult();

    try {
      assertThat(result.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
      assertThat(result.getResponseBodyContent()).isNullOrEmpty();
    } catch (AssertionError e) {
      System.out.println("----- TEST FAILED ------");
      System.out.println("body sent:\n" + body);
      System.out.println("response status: \n" + result.getStatus());
      System.out.println("response body: \n" + new String(result.getResponseBodyContent()));
      System.out.println("------------------------");

      throw e;
    }
  }

  @ParameterizedTest
  @MethodSource("com.example.itau_backend.TestFactory#provideInvalidTransactionPostBody")
  public void postTransactionWithInvalidBodyShouldReturn400(Map<String, Object> body) {
    final String uri = "/transacao";

    var result = rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .returnResult();

    try {
      assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertThat(result.getResponseBodyContent()).isNullOrEmpty();
    } catch (AssertionError e) {
      System.out.println("----- TEST FAILED ------");
      System.out.println("body sent:\n" + body);
      System.out.println("response status: \n" + result.getStatus());
      System.out.println("response body: \n" + new String(result.getResponseBodyContent()));
      System.out.println("------------------------");

      throw e;
    }
  }

  @Test
  public void postTransactionWithoutBodyShouldReturn400() {
    final String uri = "/transacao";

    rtc.post()
        .uri(uri)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody().isEmpty();
  }

  // DELETE ENDPOINT

  @Test
  public void deleteTransactionsShouldReturn200AndClearDb() {
    final int nOfTransaction = 10;
    final var transactions = TestFactory.provideValidTransactions(nOfTransaction);

    transactions.forEach(repo::save);

    assertThat(repo.findAll().count()).isEqualTo(nOfTransaction);

    final String uri = "/transacao";

    rtc.delete()
        .uri(uri)
        .exchange()
        .expectStatus().isOk()
        .expectBody().isEmpty();

    assertThat(repo.findAll().count()).isEqualTo(0);
  }

  // STATISTICS ENDPOINT

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
