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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DiskMetricsTest {
  @Test
  @DisplayName("Constructor sets fields correctly")
  void testConstructorAndGetters() {
    // Arrange
    String mountPoint = "/myMount";
    long totalSpace = 1_000L;
    long usedSpace = 200L;

    // Act
    DiskMetrics dm = new DiskMetrics(mountPoint, totalSpace, usedSpace);

    // Assert
    assertEquals(mountPoint, dm.getMountPoint());
    assertEquals(totalSpace, dm.getTotalSpace());
    assertEquals(usedSpace, dm.getUsedSpace());
  }

  @Test
  @DisplayName("toString returns expected format")
  void testToString() {
    // Arrange
    DiskMetrics dm = new DiskMetrics("/data", 2_000L, 500L);

    // Act
    String result = dm.toString();

    // Assert
    assertEquals("Disk /data: 500/2000 used", result);
  }
}
