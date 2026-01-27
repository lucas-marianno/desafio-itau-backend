package com.example.itau_backend.statistics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itau_backend.statistics.dto.TransactionStatisticsResponse;
import com.example.itau_backend.statistics.service.StatisticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estatistica")
public class StatisticsController {

  final StatisticsService statisticsService;

  @GetMapping
  public ResponseEntity<TransactionStatisticsResponse> getStatistics(
      @RequestParam(required = false) Integer segundos) {

    final var stats = segundos == null
        ? statisticsService.getStatistics()
        : statisticsService.getStatistics(segundos);

    return ResponseEntity.ok(stats);
  }

}
