package com.example.itau_backend.health.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itau_backend.health.dto.outbound.HealthStatusResponse;
import com.example.itau_backend.health.service.HealthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

  final HealthService healthService;

  @GetMapping
  public ResponseEntity<HealthStatusResponse> getHealth() {
    return ResponseEntity.ok(healthService.getServerHealth());
  }

}
