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
package org.torch.pulse.logger.framework.log4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torch.pulse.logger.core.PulseLogger;
import org.torch.pulse.logger.core.PulseLoggingFrameworkAdapter;

public class Log4J2LoggingAdapterTest {

  private final PulseLoggingFrameworkAdapter adapter = new Log4J2LoggingAdapter();

  @Test
  void getLoggerByClassReturnsNonNullLogger() {
    PulseLogger logger = adapter.getLogger(Log4J2LoggingAdapterTest.class);
    assertNotNull(logger);
  }

  @Test
  void getLoggerByNameReturnsNonNullLogger() {
    PulseLogger logger = adapter.getLogger("testLogger");
    assertNotNull(logger);
  }

  @Test
  void getLoggerByClassReturnsCorrectLogger() {
    Logger expectedLogger = LoggerFactory.getLogger(Log4J2LoggingAdapterTest.class);
    PulseLogger logger = adapter.getLogger(Log4J2LoggingAdapterTest.class);
    assertEquals(expectedLogger.getName(), ((Log4J2Logger) logger).getName());
  }

  @Test
  void getLoggerByNameReturnsCorrectLogger() {
    Logger expectedLogger = LoggerFactory.getLogger("testLogger");
    PulseLogger logger = adapter.getLogger("testLogger");
    assertEquals(expectedLogger.getName(), ((Log4J2Logger) logger).getName());
  }
}
