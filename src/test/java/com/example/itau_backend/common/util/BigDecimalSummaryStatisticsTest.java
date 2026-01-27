package com.example.itau_backend.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.params.provider.Arguments;

public class BigDecimalSummaryStatisticsTest {

  @ParameterizedTest
  @MethodSource("provideDoubleArrays")
  void shouldBehaveLikeDoubleSummaryStatistics(Double[] arr) {
    final var bigDecimalSummaryStatistics = new BigDecimalSummaryStatistics(
        Arrays.stream(arr).map(n -> new BigDecimal(n)));
    final var doubleSummaryStatistics = Arrays.stream(arr).mapToDouble(n -> n).summaryStatistics();

    assertThat(bigDecimalSummaryStatistics.getCount()).isEqualTo(doubleSummaryStatistics.getCount());
    assertThat(
        compareBigDecimalToDouble(
            bigDecimalSummaryStatistics.getSum(),
            doubleSummaryStatistics.getSum()))
        .isEqualTo(0);
    assertThat(
        compareBigDecimalToDouble(
            bigDecimalSummaryStatistics.getMin(),
            doubleSummaryStatistics.getMin()))
        .isEqualTo(0);
    assertThat(
        compareBigDecimalToDouble(
            bigDecimalSummaryStatistics.getMax(),
            doubleSummaryStatistics.getMax()))
        .isEqualTo(0);
    assertThat(
        compareBigDecimalToDouble(
            bigDecimalSummaryStatistics.getAvg(),
            doubleSummaryStatistics.getAverage()))
        .isEqualTo(0);
  }

  private static int compareBigDecimalToDouble(BigDecimal bd, Double d) {
    if (d.isInfinite() || d.isNaN())
      d = 0.0;

    final var fromDouble = BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_EVEN);

    return bd.compareTo(fromDouble);
  }

  private static Stream<Arguments> provideDoubleArrays() {
    Double[] arr = { 12.2, 234.4, 3465.6, 2345.6543, 1324.1, 12.0, 66.6 };
    Double[] empty = {};

    return Stream.of(
        Arguments.of((Object) arr),
        Arguments.of((Object) empty));
  }
}
