package com.example.itau_backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itau_backend.dto.inbound.TransactionRequest;
import com.example.itau_backend.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TransactionController {

  final TransactionService tService;

  @PostMapping("/hello")
  public ResponseEntity<Map<String, Object>> hello(
      @RequestBody final Map<String, Object> body) {
    return ResponseEntity.ok(body);
  }

  @PostMapping("/transacao")
  public ResponseEntity<Void> saveTransaction(
      @Valid @RequestBody final TransactionRequest request) {

    tService.save(request);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
