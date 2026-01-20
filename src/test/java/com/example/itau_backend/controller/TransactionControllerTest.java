package com.example.itau_backend.controller;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.RestTestClient;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureRestTestClient
public class TransactionControllerTest {

  @Autowired
  RestTestClient rtc;

  @ParameterizedTest
  @MethodSource("com.example.itau_backend.TestBodyFactory#provideValidTransactionPostBody")
  public void sanityCheck(Map<String, Object> body) {
    final String uri = "/hello";
    final String jsonBody = (new ObjectMapper()).writeValueAsString(body);

    rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(jsonBody);
  }

  @ParameterizedTest
  @MethodSource("com.example.itau_backend.TestBodyFactory#provideValidTransactionPostBody")
  public void postTransactionShouldReturn201(Map<String, Object> body) {
    final String uri = "/transacao";

    rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .expectStatus().isCreated();
  }

  @ParameterizedTest
  @MethodSource("com.example.itau_backend.TestBodyFactory#provideInvalidTransactionPostBody")
  public void postTransactionWithInvalidBodyShouldReturn422(Map<String, Object> body) {
    final String uri = "/transacao";

    rtc.post()
        .uri(uri)
        .body(body)
        .exchange()
        .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_CONTENT);
  }

  @Test
  public void postTransactionWithoutBodyShouldReturn400() {
    final String uri = "/transacao";

    rtc.post()
        .uri(uri)
        .exchange()
        .expectStatus().isBadRequest();
  }

}
