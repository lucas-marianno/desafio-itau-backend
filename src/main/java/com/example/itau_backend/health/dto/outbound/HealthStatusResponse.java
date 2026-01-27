package com.example.itau_backend.health.dto.outbound;

import java.time.OffsetDateTime;

import lombok.Builder;

@Builder
public record HealthStatusResponse(
    ServerStatus serverStatus,
    String serverUptime,
    OffsetDateTime serverLocalTime,
    SystemResources systemResources,
    StorageStatus storageStatus) {

  @Builder
  public record SystemResources(
      String totalServerMemory,
      String usedMemory,
      String serverMemoryUsage,
      Integer threadCount) {
  }

  @Builder
  public record StorageStatus(
      Long totalRecords,
      String elapsedTimeFromLastRequest) {
  }

  public enum ServerStatus {
    UP, DOWN
  }
}
