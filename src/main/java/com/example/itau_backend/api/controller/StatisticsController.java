package com.example.itau_backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itau_backend.api.dto.outbound.TransactionStatisticsResponse;
import com.example.itau_backend.api.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estatistica")
public class StatisticsController {

  final TransactionService transactionService;

  @GetMapping
  public ResponseEntity<TransactionStatisticsResponse> getStatistics(
      @RequestParam(required = false) Integer segundos) {

    final var stats = segundos == null
        ? transactionService.getStatistics()
        : transactionService.getStatistics(segundos);

    return ResponseEntity.ok(stats);
  }

}
