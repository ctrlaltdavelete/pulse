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
package org.torch.pulse_logger.framework.slf4j;

import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;
import org.torch.pulse_logger.core.PulseLogger;
import org.torch.pulse_logger.utils.PulseLoggerKeyValueMap;

public final class SLF4JLogger implements PulseLogger {

  private final Logger logger;

  public SLF4JLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void trace(String message, Object... args) {
    if (logger.isTraceEnabled()) {
      logger.trace(message, args);
    }
  }

  @Override
  public void debug(String message, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(message, args);
    }
  }

  @Override
  public void info(String message, Object... args) {
    if (logger.isInfoEnabled()) {
      logger.info(message, args);
    }
  }

  @Override
  public void warn(String message, Object... args) {
    if (logger.isWarnEnabled()) {
      logger.warn(message, args);
    }
  }

  @Override
  public void error(String message, Object... args) {
    if (logger.isErrorEnabled()) {
      logger.error(message, args);
    }
  }

  @Override
  public void error(String message, Throwable throwable) {
    if (logger.isErrorEnabled()) {
      String logMessage =
          (throwable instanceof RuntimeException re)
              ? message + " - RuntimeException occurred: " + re.getMessage()
              : message;

      logger.error(logMessage, throwable);
    }
  }

  // Structured logging
  @Override
  public void trace(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isTraceEnabled()) {
      LoggingEventBuilder builder = logger.atTrace();
      addKeyValue(builder, keyValueMap);
      builder.log(message);
    }
  }

  // Structured logging
  @Override
  public void debug(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isDebugEnabled()) {
      LoggingEventBuilder builder = logger.atDebug();
      addKeyValue(builder, keyValueMap);
      builder.log(message);
    }
  }

  // Structured logging
  @Override
  public void info(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isInfoEnabled()) {
      LoggingEventBuilder builder = logger.atInfo();
      addKeyValue(builder, keyValueMap);
      builder.log(message);
    }
  }

  // Structured logging
  @Override
  public void warn(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isWarnEnabled()) {
      LoggingEventBuilder builder = logger.atWarn();
      addKeyValue(builder, keyValueMap);
      builder.log(message);
    }
  }

  @Override
  public void error(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isErrorEnabled()) {
      LoggingEventBuilder builder = logger.atError();
      addKeyValue(builder, keyValueMap);
      builder.log(message);
    }
  }

  @Override
  public String getName() {
    return logger.getName();
  }

  // Helper method to add key-value pairs to the builder
  private void addKeyValue(LoggingEventBuilder builder, PulseLoggerKeyValueMap keyValueMap) {
    if (keyValueMap != null) {
      keyValueMap
          .getKeyValueMap()
          .forEach(
              (key, value) -> {
                builder.addKeyValue(key, value != null ? value : "null");
              });
    }
  }
}
