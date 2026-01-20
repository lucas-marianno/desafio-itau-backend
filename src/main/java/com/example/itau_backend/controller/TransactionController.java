package com.example.itau_backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  @PostMapping("/hello")
  public ResponseEntity<Map<String, Object>> hello(
      @RequestBody Map<String, Object> body) {
    return ResponseEntity.ok(body);
  }
}
