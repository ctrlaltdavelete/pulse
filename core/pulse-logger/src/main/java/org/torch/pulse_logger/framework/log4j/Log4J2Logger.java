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
package org.torch.pulse_logger.framework.log4j;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.torch.pulse_logger.core.PulseLogger;
import org.torch.pulse_logger.utils.PulseLoggerKeyValueMap;

public final class Log4J2Logger implements PulseLogger {

  private final Logger logger;

  public Log4J2Logger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void trace(String message, Object... args) {
    if (logger.isTraceEnabled()) {
      logger.trace(String.format(message, args));
    }
  }

  @Override
  public void debug(String message, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(message, args));
    }
  }

  @Override
  public void info(String message, Object... args) {
    if (logger.isInfoEnabled()) {
      logger.info(String.format(message, args));
    }
  }

  @Override
  public void warn(String message, Object... args) {
    if (logger.isWarnEnabled()) {
      logger.warn(String.format(message, args));
    }
  }

  @Override
  public void error(String message, Object... args) {
    if (logger.isErrorEnabled()) {
      logger.error(String.format(message, args));
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

  // MDC log wrappers
  @Override
  public void trace(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isTraceEnabled()) {
      try {
        populateThreadContext(keyValueMap);
        logger.trace(message);
      } finally {
        // Clear the MDC after logging
        ThreadContext.clearMap();
      }
    }
  }

  @Override
  public void debug(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isDebugEnabled()) {
      try {
        populateThreadContext(keyValueMap);
        logger.debug(message);
      } finally {
        // Clear the MDC after logging
        ThreadContext.clearMap();
      }
    }
  }

  @Override
  public void info(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isInfoEnabled()) {
      try {
        populateThreadContext(keyValueMap);
        logger.info(message);
      } finally {
        // Clear the MDC after logging
        ThreadContext.clearMap();
      }
    }
  }

  @Override
  public void warn(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isWarnEnabled()) {
      try {
        populateThreadContext(keyValueMap);
        logger.warn(message);
      } finally {
        // Clear the MDC after logging
        ThreadContext.clearMap();
      }
    }
  }

  @Override
  public void error(String message, PulseLoggerKeyValueMap keyValueMap) {
    if (logger.isErrorEnabled()) {
      try {
        populateThreadContext(keyValueMap);
        logger.error(message);
      } finally {
        // Clear the MDC after logging
        ThreadContext.clearMap();
      }
    }
  }

  @Override
  public String getName() {
    return logger.getName();
  }

  /**
   * Populates ThreadContext with key-value pairs, converting non-null values to strings and
   * replacing nulls with the string "null".
   *
   * @param keyValueMap the map of key-value pairs to populate
   */
  private void populateThreadContext(PulseLoggerKeyValueMap keyValueMap) {
    if (keyValueMap == null || keyValueMap.keyValuePairs() == null) {
      return;
    }

    keyValueMap
        .keyValuePairs()
        .forEach((key, value) -> ThreadContext.put(key, value != null ? value.toString() : "null"));
  }
}
