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

import java.util.List;
import org.torch.pulse.logger.core.PulseLogger;
import org.torch.pulse.logger.core.PulseLoggerFactory;

public class SystemMetrics {

  private static final PulseLogger logger = PulseLoggerFactory.getLogger("SystemMetricsLogger");

  private final double cpuUsage;
  private final long totalMemory;
  private final long availableMemory;
  private final double cpuTemperature;
  private final List<DiskMetrics> diskUsage;

  public SystemMetrics(double cpuUsage, long totalMemory, long availableMemory,
      double cpuTemperature, List<DiskMetrics> diskUsage) {
    logger.debug(
        "Creating SystemMetrics for cpuUsage={}, totalMemory={}, availableMemory={}, cpuTemperature={}, diskUsage={}",
        cpuUsage, totalMemory, availableMemory, cpuTemperature, diskUsage.toString());
    this.cpuUsage = cpuUsage;
    this.totalMemory = totalMemory;
    this.availableMemory = availableMemory;
    this.cpuTemperature = cpuTemperature;
    this.diskUsage = diskUsage;
  }

  public double getCpuUsage() {
    return cpuUsage;
  }

  public long getTotalMemory() {
    return totalMemory;
  }

  public long getAvailableMemory() {
    return availableMemory;
  }

  public double getCpuTemperature() {
    return cpuTemperature;
  }

  public List<DiskMetrics> getDiskUsage() {
    return diskUsage;
  }

  @Override
  public String toString() {
    return String.format("CPU Usage: %.2f%%, Memory: %d/%d, Temp: %.1f°C, Disks: %s", cpuUsage,
        availableMemory, totalMemory, cpuTemperature, diskUsage);
  }
}
