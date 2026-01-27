package com.example.itau_backend.statistics.dto;

import java.math.BigDecimal;

import com.example.itau_backend.common.util.BigDecimalSummaryStatistics;

import lombok.Builder;

@Builder
public record TransactionStatisticsResponse(
    Long count,
    BigDecimal sum,
    BigDecimal avg,
    BigDecimal min,
    BigDecimal max) {

  static public TransactionStatisticsResponse fromBigDecimalSummaryStatistics(BigDecimalSummaryStatistics stats) {
    return TransactionStatisticsResponse
        .builder()
        .count(stats.getCount())
        .sum(stats.getSum())
        .avg(stats.getAvg())
        .min(stats.getMin())
        .max(stats.getMax())
        .build();
  }
}
