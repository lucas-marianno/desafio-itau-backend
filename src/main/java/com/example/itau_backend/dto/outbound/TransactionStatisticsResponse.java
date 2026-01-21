package com.example.itau_backend.dto.outbound;

import java.util.DoubleSummaryStatistics;

import lombok.Builder;

@Builder
public record TransactionStatisticsResponse(
    Long count,
    Double sum,
    Double avg,
    Double min,
    Double max) {

  static public TransactionStatisticsResponse fromDoubleSummaryStatistics(DoubleSummaryStatistics stats) {
    return TransactionStatisticsResponse
        .builder()
        .count(stats.getCount())
        .sum(stats.getSum())
        .avg(stats.getAverage())
        .min(stats.getMin() == Double.POSITIVE_INFINITY ? 0.0 : stats.getMin())
        .max(stats.getMax() == Double.NEGATIVE_INFINITY ? 0.0 : stats.getMax())
        .build();
  }
}
