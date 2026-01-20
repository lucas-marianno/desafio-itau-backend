package com.example.itau_backend;

import java.util.Map;
import java.util.stream.Stream;

public class TestBodyFactory {

  public static Stream<Map<String, Object>> provideValidTransactionPostBody() {
    return Stream.of(
        Map.of(
            "valor", 123.45,
            "dataHora", "2020-08-07T12:34:56.789-03:00"));
  }

  public static Stream<Map<String, Object>> provideInvalidTransactionPostBody() {
    return Stream.of(
        Map.of("Valor", 123.45, "data-Hora", "2020-08-07T12:34:56.789-03:00"),
        Map.of("valor", 123.45),
        Map.of("dataHora", "2020-08-07T12:34:56.789-03:00"),
        Map.of("valor", "abc", "dataHora", "2020-08-07T12:34:56.789-03:00"),
        Map.of("valor", 123.45, "dataHora", "not-time"),
        Map.of("vakjkjkjlor", 123.45, "dataHora", "2020-08-07T12:34:56.789-03:00"));
  }
}
