package com.example.itau_backend;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import com.example.itau_backend.model.Transaction;

public class TestFactory {
  private static final Random rnd = new Random();

  private static final String futureDateTime = OffsetDateTime.now().plusDays(1).toString();
  private static final String pastDateTime = OffsetDateTime.now().minusSeconds(1).toString();

  private static final double positiveNonZero = rnd.nextDouble(0.1, 1_000_000);
  private static final double negativeNonZero = rnd.nextDouble(-1_000_000, -0.1);
  private static final double zero = 0.0;

  private static final String emptyValue = "";
  private static final String valorKey = "valor";
  private static final String dataHoraKey = "dataHora";

  public static Stream<Map<String, Object>> provideValidTransactionPostBody() {
    // A transação DEVE ter acontecido a qualquer momento no passado
    // A transação DEVE ter valor igual ou maior que 0 (zero)
    return Stream.of(
        Map.of(valorKey, positiveNonZero, dataHoraKey, pastDateTime),
        Map.of(valorKey, zero, dataHoraKey, pastDateTime));
  }

  public static Stream<Map<String, Object>> provideIllegalTransactionPostBody() {
    return Stream.of(
        // A transação NÃO DEVE acontecer no futuro
        Map.of(valorKey, positiveNonZero, dataHoraKey, futureDateTime),
        Map.of(valorKey, zero, dataHoraKey, futureDateTime),
        // A transação NÃO DEVE ter valor negativo
        Map.of(valorKey, negativeNonZero, dataHoraKey, pastDateTime),
        Map.of(valorKey, negativeNonZero, dataHoraKey, futureDateTime));
  }

  public static Stream<Map<String, Object>> provideInvalidTransactionPostBody() {
    return Stream.of(
        // Valores nulos
        Map.of(valorKey, emptyValue, dataHoraKey, emptyValue),
        Map.of(valorKey, emptyValue, dataHoraKey, pastDateTime),
        Map.of(valorKey, positiveNonZero, dataHoraKey, emptyValue),
        Map.of(valorKey, zero, dataHoraKey, emptyValue),
        // Parametros faltantes
        Map.of(valorKey, positiveNonZero),
        Map.of(dataHoraKey, pastDateTime),
        // Chaves inválidas
        Map.of("abc", positiveNonZero, dataHoraKey, pastDateTime),
        Map.of("Valor", positiveNonZero, dataHoraKey, pastDateTime),
        Map.of(valorKey, positiveNonZero, "abc", pastDateTime),
        Map.of(valorKey, positiveNonZero, "data-hora", pastDateTime),
        Map.of(emptyValue, positiveNonZero, dataHoraKey, pastDateTime),
        Map.of(valorKey, positiveNonZero, emptyValue, pastDateTime),
        // Valoeres inválidos
        Map.of(valorKey, "abc", dataHoraKey, pastDateTime),
        Map.of(valorKey, positiveNonZero, dataHoraKey, "abc"));
  }

  public static Stream<Transaction> provideValidTransaction(final int nOfTransactions) {
    List<Transaction> s = new ArrayList<>(nOfTransactions);

    for (int i = 0; i < nOfTransactions; i++) {
      s.add(provideValidTransaction());
    }

    return s.stream();
  }

  public static Transaction provideValidTransaction() {
    final var rnd = new Random();
    return Transaction.builder()
        .dataHora(OffsetDateTime.now().minusSeconds(rnd.nextInt(0, 1000)))
        .valor(rnd.nextDouble(0, 1_000_000))
        .build();
  }
}
