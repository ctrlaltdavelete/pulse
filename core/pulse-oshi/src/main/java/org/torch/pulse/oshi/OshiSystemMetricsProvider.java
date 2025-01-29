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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.torch.pulse.logger.core.PulseLogger;
import org.torch.pulse.logger.core.PulseLoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

/**
 * 
 */
public final class OshiSystemMetricsProvider implements SystemMetricsProvider {

  private static final PulseLogger logger =
      PulseLoggerFactory.getLogger("OshiSystemMetricsProviderLogger");

  private final HardwareAbstractionLayer hardware;
  private final OperatingSystem os;
  private final CentralProcessor processor;
  private final GlobalMemory memory;
  private final Sensors sensors;
  private long[] previousTicks;

  public OshiSystemMetricsProvider() {
    logger.debug("Initializing OshiSystemMetricsProvider...");
    SystemInfo systemInfo = new SystemInfo();
    this.hardware = systemInfo.getHardware();
    this.os = systemInfo.getOperatingSystem();
    this.processor = hardware.getProcessor();
    this.previousTicks = processor.getSystemCpuLoadTicks(); // Capture initial tick snapshot
    this.memory = hardware.getMemory();
    this.sensors = hardware.getSensors();
    logger.info("OshiSystemMetricsProvider initialized successfully.");
  }

  /**
   * Package-private constructor for testing.
   */
  OshiSystemMetricsProvider(HardwareAbstractionLayer hardware, OperatingSystem os,
      CentralProcessor processor, GlobalMemory memory, Sensors sensors, long[] initialTicks) {
    logger.debug("Initializing OshiSystemMetricsProvider (test mode)...");
    this.hardware = hardware;
    this.os = os;
    this.processor = processor;
    this.memory = memory;
    this.sensors = sensors;
    this.previousTicks = initialTicks;
    logger.info("OshiSystemMetricsProvider initialized (test mode).");
  }

  @Override
  public double getCpuUsage() {
    logger.debug("Attempting to measure CPU usage...");
    try {
      TimeUnit.MILLISECONDS.sleep(500); // Short delay for accurate measurement
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return -1.0; // Return -1 on error
    }

    // Capture new tick snapshot and calculate CPU load
    double cpuLoad = processor.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
    previousTicks = processor.getSystemCpuLoadTicks(); // Update tick snapshot
    logger.info("Current CPU usage is {}%", String.format("%.2f", cpuLoad));

    return cpuLoad;
  }

  @Override
  public long getTotalMemory() {
    long totalMem = memory.getTotal();
    logger.debug("Total memory: {} bytes", totalMem);
    return totalMem;
  }

  @Override
  public long getAvailableMemory() {
    long availableMem = memory.getAvailable();
    logger.debug("Available memory: {} bytes", availableMem);
    return availableMem;
  }

  @Override
  public double getCpuTemperature() {
    double temp = sensors.getCpuTemperature();
    logger.debug("CPU temperature: {} degrees Celsius", temp);
    return temp;
  }

  @Override
  public List<DiskMetrics> getDiskUsage() {
    logger.debug("Retrieving disk usage information...");
    List<DiskMetrics> diskMetrics = new ArrayList<>();
    for (OSFileStore fs : os.getFileSystem().getFileStores()) {
      long totalSpace = fs.getTotalSpace();
      long usableSpace = fs.getUsableSpace();
      long usedSpace = totalSpace - usableSpace;
      String mount = fs.getMount();
      diskMetrics.add(new DiskMetrics(mount, totalSpace, usedSpace));
      logger.debug("Disk: {} | Total: {} | Used: {} | Usable: {}", mount, totalSpace, usedSpace,
          usableSpace);
    }
    logger.info("Disk usage information retrieved for {} file stores.", diskMetrics.size());
    return diskMetrics;
  }
}
