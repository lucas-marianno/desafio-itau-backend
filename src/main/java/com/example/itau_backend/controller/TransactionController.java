package com.example.itau_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itau_backend.dto.inbound.TransactionRequest;
import com.example.itau_backend.dto.outbound.TransactionStatisticsResponse;
import com.example.itau_backend.model.Transaction;
import com.example.itau_backend.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TransactionController {

  final TransactionService service;

  @PostMapping("/hello")
  public ResponseEntity<Map<String, Object>> hello(
      @RequestBody final Map<String, Object> body) {
    return ResponseEntity.ok(body);
  }

  @PostMapping("/transacao")
  public ResponseEntity<Void> saveTransaction(
      @Valid @RequestBody final TransactionRequest request) {

    service.save(request);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/transacao")
  public ResponseEntity<Void> deleteAllTransaction() {
    service.deleteAll();

    return ResponseEntity.ok().build();
  }

  @GetMapping("/estatistica")
  public ResponseEntity<TransactionStatisticsResponse> getStatistics(
      @RequestParam(required = false) Integer segundos) {

    final var stats = segundos == null
        ? service.getStatistics()
        : service.getStatistics(segundos);

    return ResponseEntity.ok(stats);
  }

  @GetMapping("/debug/get-all")
  public ResponseEntity<List<Transaction>> getAll() {
    return ResponseEntity.ok(service.findAll());
  }

}
