package com.example.itau_backend.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itau_backend.api.dto.TransactionRequest;
import com.example.itau_backend.api.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacao")
public class TransactionController {

  final TransactionService transactionService;

  @PostMapping
  public ResponseEntity<Void> saveTransaction(
      @Valid @RequestBody final TransactionRequest request) {

    transactionService.save(request);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteAllTransaction() {
    transactionService.deleteAll();

    return ResponseEntity.ok().build();
  }

}
