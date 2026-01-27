package com.example.itau_backend.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.itau_backend.health.dto.outbound.HealthStatusResponse;

@SpringBootTest
@AutoConfigureRestTestClient
public class HealthControllerTest {

  @Autowired
  RestTestClient rtc;

  // HEALTH ENDPOINT
  @Test
  public void getHealthShouldReturn200() {
    final var uri = "/health";

    rtc.get()
        .uri(uri)
        .exchange()
        .expectStatus().isOk()
        .expectBody(HealthStatusResponse.class);
  }
}
