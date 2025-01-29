/*
 * Copyright 2025 Torch
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.torch.pulse.oshi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SystemMetricsTest {

  @Test
  @DisplayName("Constructor sets fields correctly")
  void testConstructorAndGetters() {
    // Arrange
    double cpuUsage = 43.21;
    long totalMemory = 16_000_000L;
    long availableMemory = 8_000_000L;
    double cpuTemp = 55.0;

    List<DiskMetrics> disks = new ArrayList<>();
    disks.add(new DiskMetrics("/mnt", 1000L, 400L));

    // Act
    SystemMetrics systemMetrics =
        new SystemMetrics(cpuUsage, totalMemory, availableMemory, cpuTemp, disks);

    // Assert
    assertEquals(cpuUsage, systemMetrics.getCpuUsage(), 0.0001);
    assertEquals(totalMemory, systemMetrics.getTotalMemory());
    assertEquals(availableMemory, systemMetrics.getAvailableMemory());
    assertEquals(cpuTemp, systemMetrics.getCpuTemperature(), 0.0001);
    assertEquals(disks, systemMetrics.getDiskUsage());
  }

  @Test
  @DisplayName("toString returns expected format")
  void testToString() {
    // Arrange
    double cpuUsage = 12.34;
    long totalMemory = 10_000L;
    long availableMemory = 4_000L;
    double cpuTemp = 60.5;

    List<DiskMetrics> disks = new ArrayList<>();
    disks.add(new DiskMetrics("/data", 2000L, 500L));

    SystemMetrics systemMetrics =
        new SystemMetrics(cpuUsage, totalMemory, availableMemory, cpuTemp, disks);

    // Act
    String actualString = systemMetrics.toString();

    // Assert
    // Example: "CPU Usage: 12.34%, Memory: 4000/10000, Temp: 60.5°C, Disks: [Disk /data: 500/2000
    // used]"
    // We can do a partial match or the full string. For simplicity, we'll verify some key parts.
    assertTrue(actualString.contains("CPU Usage: 12.34%"), "Expected CPU usage in string");
    assertTrue(actualString.contains("Memory: 4000/10000"), "Expected memory usage in string");
    assertTrue(actualString.contains("Temp: 60.5°C"), "Expected temperature in string");
    assertTrue(actualString.contains("Disks: [Disk /data: 500/2000 used]"),
        "Expected disk usage in string");
  }
}
