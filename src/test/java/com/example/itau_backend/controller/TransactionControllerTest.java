package com.example.itau_backend.controller;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
public class TransactionControllerTest {

  @Autowired
  RestTestClient rtc;

  @ParameterizedTest
  @MethodSource("provideValidTrasactionPostBody")
  public void postTransactionShouldReturn201(Map<String, Object> body) {
    final String uri = "";

    rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .expectStatus().isCreated();
  }

  public static Stream<Map<String, Object>> provideValidTrasactionPostBody() {
    return Stream.of(
        Map.of(
            "valor", 123.45,
            "dataHora", "2020-08-07T12:34:56.789-03:00"));
  }
}
