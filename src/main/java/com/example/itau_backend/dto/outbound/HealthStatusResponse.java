
package com.example.itau_backend.dto.outbound;

import java.time.OffsetDateTime;

import lombok.Builder;

// healthcheck endpoint {
//   basics:{
//     status: UP or DOWN
//     uptime:
//     timestamp: the current servertime
//   }
//   system resources:{
//     memory:{
//       total available memory
//       used memory
//     }
//     thread count:
//   }
//   storage:{
//     n of records stored
//     last transaction timestamp
//   }
// }
@Builder
public record HealthStatusResponse(
    ServerStatus serverStatus,
    String serverUptime,
    OffsetDateTime serverLocalTime,
    SystemResources systemResources,
    StorageStatus storageStatus

) {
  enum ServerStatus { UP, DOWN }

  public record SystemResources(
      Long totalServerMemory,
      Long availableMemory,
      Integer threadCount
  ) { }

  public record StorageStatus(
      Integer totalRecords,
      OffsetDateTime lastTransactionTimestamp
  ) { }
}
