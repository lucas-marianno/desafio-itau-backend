
package com.example.itau_backend.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.example.itau_backend.dto.outbound.HealthStatusResponse;
import com.example.itau_backend.repository.TransactionRepository;

@Service
public class HealthService {
  private final TransactionRepository repo;
  private final long startTime = System.currentTimeMillis();
  private volatile Long lastServerRequestTimestamp;

  public HealthService(final TransactionRepository repo) {
    this.repo = repo;
  }

  public void registerServerRequestTimestamp() {
    lastServerRequestTimestamp = System.currentTimeMillis();
  }

  public HealthStatusResponse getServerHealth() {
    final var serverUptime = (System.currentTimeMillis() - startTime) / 1000 + " seconds";

    return HealthStatusResponse.builder()
        .serverStatus(HealthStatusResponse.ServerStatus.UP)
        .serverUptime(serverUptime)
        .serverLocalTime(OffsetDateTime.now())
        .systemResources(getSystemResources())
        .storageStatus(getStorageStatus())
        .build();
  }

  private HealthStatusResponse.StorageStatus getStorageStatus() {
    final var totalRecords = repo.findAll().count();
    final var elapsedTimeFromLastRequest = lastServerRequestTimestamp == null ? null
        : (System.currentTimeMillis() - lastServerRequestTimestamp) / 1000 + " seconds ago";

    return HealthStatusResponse.StorageStatus.builder()
        .totalRecords(totalRecords)
        .elapsedTimeFromLastRequest(elapsedTimeFromLastRequest)
        .build();
  }

  private HealthStatusResponse.SystemResources getSystemResources() {
    final Runtime runtime = Runtime.getRuntime();
    final var usedMemory = (double) (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
    final var serverMemoryUsage = String.format("%.2f %%", ((double) usedMemory / runtime.maxMemory()) * 100);

    return HealthStatusResponse.SystemResources.builder()
        .totalServerMemory(((double) runtime.maxMemory() / (1024 * 1024)) + " MB")
        .usedMemory(String.format("%.2f MB", usedMemory))
        .serverMemoryUsage(serverMemoryUsage)
        .threadCount(Thread.activeCount())
        .build();
  }
}
