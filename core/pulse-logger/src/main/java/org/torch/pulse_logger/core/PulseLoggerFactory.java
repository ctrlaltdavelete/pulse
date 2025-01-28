/*
 * Copyright 2025 Torch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.torch.pulse_logger.core;

import java.util.concurrent.atomic.AtomicReference;
import org.torch.pulse_logger.framework.log4j.Log4J2LoggingAdapter;
import org.torch.pulse_logger.framework.slf4j.SLF4JLoggingAdapter;

public class PulseLoggerFactory {
  private static final AtomicReference<PulseLoggingFrameworkAdapter> adapterRef =
      new AtomicReference<>();

  static {
    // Set the default adapter (can be overridden at runtime)
    adapterRef.set(createAdapter("slf4j"));
  }

  /** Get a PulseLogger for a specific class. */
  public static PulseLogger getLogger(Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class cannot be null");
    }
    return adapterRef.get().getLogger(clazz);
  }

  /** Get a PulseLogger for a specific name. */
  public static PulseLogger getLogger(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Logger name cannot be null or empty");
    }
    return adapterRef.get().getLogger(name);
  }

  /** Set a custom logging framework adapter. */
  public static void setAdapter(PulseLoggingFrameworkAdapter adapter) {
    if (adapter == null) {
      throw new IllegalArgumentException("Adapter cannot be null");
    }
    adapterRef.set(adapter);
  }

  public static PulseLoggingFrameworkAdapter getAdapter() {
    return adapterRef.get();
  }

  public static PulseLoggingFrameworkAdapter createAdapter(String type) {
    return switch (type) {
      case "slf4j" -> new SLF4JLoggingAdapter();
      case "log4j" -> new Log4J2LoggingAdapter();
      default -> throw new IllegalArgumentException("Unsupported logging framework: " + type);
    };
  }
}
