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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.torch.pulse_logger.framework.log4j.Log4J2LoggingAdapter;
import org.torch.pulse_logger.framework.slf4j.SLF4JLoggingAdapter;

class PulseLoggerFactoryTest {
  @Test
  void getLoggerByClassReturnsNonNullLogger() {
    PulseLogger logger = PulseLoggerFactory.getLogger(PulseLoggerFactoryTest.class);
    assertNotNull(logger);
  }

  @Test
  void getLoggerByNameReturnsNonNullLogger() {
    PulseLogger logger = PulseLoggerFactory.getLogger("testLogger");
    assertNotNull(logger);
  }

  @Test
  void setAdapterToNullThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> PulseLoggerFactory.setAdapter(null));
  }

  @Test
  void setAdapterChangesLoggerAdapter() {
    PulseLoggingFrameworkAdapter newAdapter = new Log4J2LoggingAdapter();
    PulseLoggerFactory.setAdapter(newAdapter);
    assertEquals(Log4J2LoggingAdapter.class, PulseLoggerFactory.getAdapter().getClass());
  }

  @Test
  void createAdapterWithUnsupportedTypeThrowsException() {
    assertThrows(
        IllegalArgumentException.class, () -> PulseLoggerFactory.createAdapter("unsupported"));
  }

  @Test
  void createAdapterWithSlf4jTypeReturnsSlf4jAdapter() {
    PulseLoggingFrameworkAdapter adapter = PulseLoggerFactory.createAdapter("slf4j");
    assertEquals(SLF4JLoggingAdapter.class, adapter.getClass());
  }

  @Test
  void createAdapterWithLog4jTypeReturnsLog4jAdapter() {
    PulseLoggingFrameworkAdapter adapter = PulseLoggerFactory.createAdapter("log4j");
    assertEquals(Log4J2LoggingAdapter.class, adapter.getClass());
  }

  @Test
  void defaultAdapterIsNotNull() {
    PulseLoggingFrameworkAdapter adapter = PulseLoggerFactory.getAdapter();
    assertNotNull(adapter);
  }

  @Test
  void setAdapterTwiceChangesLoggerAdapter() {
    PulseLoggingFrameworkAdapter firstAdapter = new SLF4JLoggingAdapter();
    PulseLoggingFrameworkAdapter secondAdapter = new Log4J2LoggingAdapter();

    PulseLoggerFactory.setAdapter(firstAdapter);
    assertEquals(SLF4JLoggingAdapter.class, PulseLoggerFactory.getAdapter().getClass());

    PulseLoggerFactory.setAdapter(secondAdapter);
    assertEquals(Log4J2LoggingAdapter.class, PulseLoggerFactory.getAdapter().getClass());
  }

  @Test
  void getLoggerByNullClassThrowsException() {
    assertThrows(
        IllegalArgumentException.class, () -> PulseLoggerFactory.getLogger((Class<?>) null));
  }

  @Test
  void getLoggerByEmptyNameThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> PulseLoggerFactory.getLogger(""));
  }

  @Test
  void getLoggerByNullNameThrowsException() {
    // Test the `name == null` branch
    assertThrows(IllegalArgumentException.class, () -> PulseLoggerFactory.getLogger((String) null));
  }

  @Test
  void getLoggerByWhitespaceNameThrowsException() {
    // Test the `name.trim().isEmpty()` branch
    assertThrows(IllegalArgumentException.class, () -> PulseLoggerFactory.getLogger("   "));
  }

  @Test
  void testConcurrentAccessToGetLogger() throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    CountDownLatch latch = new CountDownLatch(10);

    for (int i = 0; i < 10; i++) {
      executorService.submit(
          () -> {
            try {
              PulseLogger logger = PulseLoggerFactory.getLogger("testLogger");
              assertNotNull(logger);
            } finally {
              latch.countDown();
            }
          });
    }

    latch.await();
    executorService.shutdown();
  }
}
