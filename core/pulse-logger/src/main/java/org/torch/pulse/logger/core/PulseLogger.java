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
package org.torch.pulse.logger.core;

import org.torch.pulse.logger.framework.log4j.Log4J2Logger;
import org.torch.pulse.logger.framework.slf4j.SLF4JLogger;
import org.torch.pulse.logger.utils.PulseLoggerKeyValueMap;

public sealed interface PulseLogger permits SLF4JLogger, Log4J2Logger {

  void trace(String message, Object... args);

  void debug(String message, Object... args);

  void info(String message, Object... args);

  void warn(String message, Object... args);

  void error(String message, Object... args); // Handles varargs formatting

  void error(String message, Throwable throwable); // Handles throwable with a message

  // Structured logging
  void trace(String message, PulseLoggerKeyValueMap keyValueMap);

  void debug(String message, PulseLoggerKeyValueMap keyValueMap);

  void info(String message, PulseLoggerKeyValueMap keyValueMap);

  void warn(String message, PulseLoggerKeyValueMap keyValueMap);

  void error(String message, PulseLoggerKeyValueMap keyValueMap);

  String getName();
}
