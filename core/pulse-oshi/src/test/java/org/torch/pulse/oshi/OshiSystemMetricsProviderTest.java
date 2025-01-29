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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

/**
 * Unit tests for OshiSystemMetricsProvider using JUnit 5 and Mockito.
 */
public class OshiSystemMetricsProviderTest {

  @Mock
  private HardwareAbstractionLayer mockHardware;

  @Mock
  private OperatingSystem mockOperatingSystem;

  @Mock
  private CentralProcessor mockProcessor;

  @Mock
  private GlobalMemory mockMemory;

  @Mock
  private Sensors mockSensors;

  @Mock
  private FileSystem mockFileSystem;

  @Mock
  private OSFileStore mockFs1;

  @Mock
  private OSFileStore mockFs2;

  private OshiSystemMetricsProvider provider;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Stubs for memory and CPU
    when(mockHardware.getProcessor()).thenReturn(mockProcessor);
    when(mockHardware.getMemory()).thenReturn(mockMemory);
    when(mockHardware.getSensors()).thenReturn(mockSensors);

    // Stubs for OS and file system
    when(mockOperatingSystem.getFileSystem()).thenReturn(mockFileSystem);

    // Provide an initial tick array for CPU usage
    long[] initialTicks = {100L, 200L, 300L, 400L};

    // Create the provider with the testing constructor
    provider = new OshiSystemMetricsProvider(mockHardware, mockOperatingSystem, mockProcessor,
        mockMemory, mockSensors, initialTicks);
  }

  @Nested
  @DisplayName("CPU Usage Tests")
  class CpuUsageTests {

    @Test
    @DisplayName("Should return correct CPU usage when not interrupted")
    void testGetCpuUsage() {
      // Arrange
      // Simulate CPU usage of 50%
      when(mockProcessor.getSystemCpuLoadBetweenTicks(any(long[].class))).thenReturn(0.5);
      when(mockProcessor.getSystemCpuLoadTicks()).thenReturn(new long[] {150L, 250L, 350L, 450L});

      // Act
      double cpuUsage = provider.getCpuUsage();

      // Assert
      assertEquals(50.0, cpuUsage, 0.001);
      verify(mockProcessor).getSystemCpuLoadBetweenTicks(any(long[].class));
      verify(mockProcessor).getSystemCpuLoadTicks();
    }

    @Test
    @DisplayName("Should return -1.0 when the sleep is interrupted")
    void testGetCpuUsageInterrupted() throws InterruptedException {
      // We can't easily force the `TimeUnit.MILLISECONDS.sleep(500)` to throw,
      // but we can forcibly interrupt the thread.
      Thread.currentThread().interrupt();

      double cpuUsage = provider.getCpuUsage();

      assertEquals(-1.0, cpuUsage, 0.001);

      // Clear interrupt flag so it doesn't affect other tests
      Thread.interrupted();
    }
  }

  @Nested
  @DisplayName("Memory Tests")
  class MemoryTests {

    @Test
    @DisplayName("Should return correct total memory")
    void testGetTotalMemory() {
      when(mockMemory.getTotal()).thenReturn(16_000_000L);
      long totalMemory = provider.getTotalMemory();
      assertEquals(16_000_000L, totalMemory);
      verify(mockMemory).getTotal();
    }

    @Test
    @DisplayName("Should return correct available memory")
    void testGetAvailableMemory() {
      when(mockMemory.getAvailable()).thenReturn(8_000_000L);
      long availableMemory = provider.getAvailableMemory();
      assertEquals(8_000_000L, availableMemory);
      verify(mockMemory).getAvailable();
    }
  }

  @Nested
  @DisplayName("CPU Temperature Tests")
  class CpuTemperatureTests {
    @Test
    @DisplayName("Should return correct CPU temperature")
    void testGetCpuTemperature() {
      when(mockSensors.getCpuTemperature()).thenReturn(45.5);
      double temp = provider.getCpuTemperature();
      assertEquals(45.5, temp, 0.001);
      verify(mockSensors).getCpuTemperature();
    }
  }

  @Nested
  @DisplayName("Disk Usage Tests")
  class DiskUsageTests {

    @Test
    @DisplayName("Should return disk metrics from multiple file stores")
    void testGetDiskUsage() {
      when(mockFileSystem.getFileStores()).thenReturn(Arrays.asList(mockFs1, mockFs2));

      // Disk #1
      when(mockFs1.getMount()).thenReturn("/mnt1");
      when(mockFs1.getTotalSpace()).thenReturn(200_000L);
      when(mockFs1.getUsableSpace()).thenReturn(100_000L);

      // Disk #2
      when(mockFs2.getMount()).thenReturn("/mnt2");
      when(mockFs2.getTotalSpace()).thenReturn(500_000L);
      when(mockFs2.getUsableSpace()).thenReturn(200_000L);

      List<DiskMetrics> result = provider.getDiskUsage();

      assertEquals(2, result.size());

      // Check first disk
      DiskMetrics dm1 = result.get(0);
      assertEquals("/mnt1", dm1.getMountPoint());
      assertEquals(200_000L, dm1.getTotalSpace());
      assertEquals(100_000L, dm1.getUsedSpace()); // 200000 - 100000

      // Check second disk
      DiskMetrics dm2 = result.get(1);
      assertEquals("/mnt2", dm2.getMountPoint());
      assertEquals(500_000L, dm2.getTotalSpace());
      assertEquals(300_000L, dm2.getUsedSpace()); // 500000 - 200000

      // Verify calls
      verify(mockFileSystem).getFileStores();
      verify(mockFs1, times(1)).getMount();
      verify(mockFs1, times(1)).getTotalSpace();
      verify(mockFs1, times(1)).getUsableSpace();
      verify(mockFs2, times(1)).getMount();
      verify(mockFs2, times(1)).getTotalSpace();
      verify(mockFs2, times(1)).getUsableSpace();
    }
  }
}
