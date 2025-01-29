package org.torch.pulse.oshi;

import java.util.List;

public sealed interface SystemMetricsProvider permits OshiSystemMetricsProvider {

  double getCpuUsage();

  long getTotalMemory();

  long getAvailableMemory();

  List<DiskMetrics> getDiskUsage();

  double getCpuTemperature();

}
