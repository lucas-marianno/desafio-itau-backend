package com.example.itau_backend.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class BigDecimalSummaryStatistics {

  private long count = 0;
  private BigDecimal sum = BigDecimal.ZERO;
  private BigDecimal avg = BigDecimal.ZERO;
  private BigDecimal min = null;
  private BigDecimal max = null;

  public BigDecimalSummaryStatistics(Stream<BigDecimal> stream) {
    stream.forEach(n -> {
      count++;
      sum = sum.add(n);

      if (min == null || n.compareTo(min) == -1)
        min = n;

      if (max == null || n.compareTo(max) == 1)
        max = n;
    });

    if (count > 0)
      avg = sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_EVEN);

    if (min == null)
      min = BigDecimal.ZERO;
    if (max == null)
      max = BigDecimal.ZERO;

    sum = sum.setScale(2, RoundingMode.HALF_EVEN);
    min = min.setScale(2, RoundingMode.HALF_EVEN);
    max = max.setScale(2, RoundingMode.HALF_EVEN);
  }
}
