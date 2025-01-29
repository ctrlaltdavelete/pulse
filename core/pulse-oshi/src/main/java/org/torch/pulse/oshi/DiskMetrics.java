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

import org.torch.pulse.logger.core.PulseLogger;
import org.torch.pulse.logger.core.PulseLoggerFactory;

public class DiskMetrics {

  private static final PulseLogger logger = PulseLoggerFactory.getLogger("DiskMetricsLogger");

  private final String mountPoint;
  private final long totalSpace;
  private final long usedSpace;

  public DiskMetrics(String mountPoint, long totalSpace, long usedSpace) {
    logger.debug("Creating DiskMetrics for mountPoint={}, totalSpace={}, usedSpace={}", mountPoint,
        totalSpace, usedSpace);
    this.mountPoint = mountPoint;
    this.totalSpace = totalSpace;
    this.usedSpace = usedSpace;
  }

  public String getMountPoint() {
    return mountPoint;
  }

  public long getTotalSpace() {
    return totalSpace;
  }

  public long getUsedSpace() {
    return usedSpace;
  }

  @Override
  public String toString() {
    logger.debug("toString called for mountPoint={}", mountPoint);
    return String.format("Disk %s: %d/%d used", mountPoint, usedSpace, totalSpace);
  }

}
