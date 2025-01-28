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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.torch.pulse.logger.utils.PulseLoggerKeyValueMap;

/** */
public class Log4J2LoggerTest {

  @InjectMocks
  private Log4J2Logger pulseLogger;

  @Mock
  private Logger mockLogger;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    pulseLogger = new Log4J2Logger(mockLogger);
  }

  @ParameterizedTest
  @MethodSource("provideTestDataSimple")
  void testLoggingDisabled(TestDataSimple testData) {
    switch (testData.level) {
      case "trace":
        when(mockLogger.isTraceEnabled()).thenReturn(false);
        pulseLogger.trace(testData.message, testData.args);
        verify(mockLogger, never()).trace(String.format(testData.message, testData.args));
        break;
      case "debug":
        when(mockLogger.isDebugEnabled()).thenReturn(false);
        pulseLogger.debug(testData.message, testData.args);
        verify(mockLogger, never()).debug(String.format(testData.message, testData.args));
        break;
      case "info":
        when(mockLogger.isInfoEnabled()).thenReturn(false);
        pulseLogger.info(testData.message, testData.args);
        verify(mockLogger, never()).info(String.format(testData.message, testData.args));
        break;
      case "warn":
        when(mockLogger.isWarnEnabled()).thenReturn(false);
        pulseLogger.warn(testData.message, testData.args);
        verify(mockLogger, never()).warn(String.format(testData.message, testData.args));
        break;
      case "error":
        when(mockLogger.isErrorEnabled()).thenReturn(false);
        pulseLogger.error(testData.message, testData.args);
        verify(mockLogger, never()).error(String.format(testData.message, testData.args));
        break;
    }
  }

  @ParameterizedTest
  @MethodSource("provideTestDataSimple")
  void testLoggingEnabled(TestDataSimple testData) {
    switch (testData.level) {
      case "trace":
        when(mockLogger.isTraceEnabled()).thenReturn(true);
        pulseLogger.trace(testData.message, testData.args);
        verify(mockLogger, times(1)).trace(String.format(testData.message, testData.args));
        break;
      case "debug":
        when(mockLogger.isDebugEnabled()).thenReturn(true);
        pulseLogger.debug(testData.message, testData.args);
        verify(mockLogger, times(1)).debug(String.format(testData.message, testData.args));
        break;
      case "info":
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        pulseLogger.info(testData.message, testData.args);
        verify(mockLogger, times(1)).info(String.format(testData.message, testData.args));
        break;
      case "warn":
        when(mockLogger.isWarnEnabled()).thenReturn(true);
        pulseLogger.warn(testData.message, testData.args);
        verify(mockLogger, times(1)).warn(String.format(testData.message, testData.args));
        break;
      case "error":
        when(mockLogger.isErrorEnabled()).thenReturn(true);
        pulseLogger.error(testData.message, testData.args);
        verify(mockLogger, times(1)).error(String.format(testData.message, testData.args));
        break;
    }
  }

  @ParameterizedTest
  @MethodSource("provideTestDataKVMap")
  void testMDCLoggingDisabled(TestDataKVMap testData) {
    switch (testData.level) {
      case "trace":
        when(mockLogger.isTraceEnabled()).thenReturn(false);
        pulseLogger.trace(testData.message, testData.keyValueMap);
        verify(mockLogger, never()).trace(testData.message);
        break;
      case "debug":
        when(mockLogger.isDebugEnabled()).thenReturn(false);
        pulseLogger.debug(testData.message, testData.keyValueMap);
        verify(mockLogger, never()).debug(testData.message);
        break;
      case "info":
        when(mockLogger.isInfoEnabled()).thenReturn(false);
        pulseLogger.info(testData.message, testData.keyValueMap);
        verify(mockLogger, never()).info(testData.message);
        break;
      case "warn":
        when(mockLogger.isWarnEnabled()).thenReturn(false);
        pulseLogger.warn(testData.message, testData.keyValueMap);
        verify(mockLogger, never()).warn(testData.message);
        break;
      case "error":
        when(mockLogger.isErrorEnabled()).thenReturn(false);
        pulseLogger.error(testData.message, testData.keyValueMap);
        verify(mockLogger, never()).error(testData.message);
        break;
    }
  }

  @ParameterizedTest
  @MethodSource("provideTestDataKVMap")
  void testMDCLoggingEnabled(TestDataKVMap testData) {
    switch (testData.level) {
      case "trace":
        when(mockLogger.isTraceEnabled()).thenReturn(true);
        pulseLogger.trace(testData.message, testData.keyValueMap);
        verify(mockLogger, times(1)).trace(testData.message);
        break;
      case "debug":
        when(mockLogger.isDebugEnabled()).thenReturn(true);
        pulseLogger.debug(testData.message, testData.keyValueMap);
        verify(mockLogger, times(1)).debug(testData.message);
        break;
      case "info":
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        pulseLogger.info(testData.message, testData.keyValueMap);
        verify(mockLogger, times(1)).info(testData.message);
        break;
      case "warn":
        when(mockLogger.isWarnEnabled()).thenReturn(true);
        pulseLogger.warn(testData.message, testData.keyValueMap);
        verify(mockLogger, times(1)).warn(testData.message);
        break;
      case "error":
        when(mockLogger.isErrorEnabled()).thenReturn(true);
        pulseLogger.error(testData.message, testData.keyValueMap);
        verify(mockLogger, times(1)).error(testData.message);
        break;
    }
  }

  @Test
  void testErrorWithRuntimeExceptionDisabled() {
    // Arrange
    String message = "Test error message";
    Throwable throwable = new RuntimeException("Test exception");

    when(mockLogger.isErrorEnabled()).thenReturn(false);

    // Act
    pulseLogger.error(message, throwable);

    // Assert
    verify(mockLogger, never())
        .error("Test error message - RuntimeException occurred: Test exception", throwable);
  }

  @Test
  void testErrorWithRuntimeExceptionEnabled() {
    // Arrange
    String message = "Test error message";
    Throwable throwable = new RuntimeException("Test exception");

    when(mockLogger.isErrorEnabled()).thenReturn(true);

    // Act
    pulseLogger.error(message, throwable);

    // Assert
    verify(mockLogger, times(1))
        .error("Test error message - RuntimeException occurred: Test exception", throwable);
  }

  @Test
  void testErrorWithNonRuntimeException() {
    // Arrange
    String message = "Test error message";
    Throwable throwable = new NoSuchMethodException("Test exception");

    when(mockLogger.isErrorEnabled()).thenReturn(true);

    // Act
    pulseLogger.error(message, throwable);

    // Assert
    verify(mockLogger, times(1)).error(message, throwable);
  }

  @Test
  void testGetName() {
    // Arrange
    String expectedName = "TestLogger";
    when(mockLogger.getName()).thenReturn(expectedName);

    // Act
    String actualName = pulseLogger.getName();

    // Assert
    assertEquals(expectedName, actualName, "The logger name should match the expected name");
  }

  @Test
  void testGetNameHandlesNull() {
    // Arrange
    when(mockLogger.getName()).thenReturn(null);

    // Act
    String actualName = pulseLogger.getName();

    // Assert
    assertEquals(null, actualName, "The logger name should handle null values properly");
  }

  // TestDataSimple class
  private static class TestDataSimple {
    final String level;
    final String message;
    final Object[] args;

    TestDataSimple(String level, String message, Object... args) {
      this.level = level;
      this.message = message;
      this.args = args;
    }

    @Override
    public String toString() {
      return "TestDataSimple{" + "level='" + level + '\'' + ", message='" + message + '\''
          + ", args=" + Arrays.toString(args) + '}';
    }
  }

  private static Stream<TestDataSimple> provideTestDataSimple() {
    return Stream.of(new TestDataSimple("trace", "Trace message", "test"),
        new TestDataSimple("debug", "Debug message", "arg1"),
        new TestDataSimple("info", "Info message", "arg1", 42),
        new TestDataSimple("warn", "Warn message", "arg1", 43),
        new TestDataSimple("error", "Error message", new RuntimeException("Test exception")));
  }

  private static class TestDataKVMap {
    final String level;
    final String message;
    final PulseLoggerKeyValueMap keyValueMap;

    TestDataKVMap(String level, String message, PulseLoggerKeyValueMap keyValueMap) {
      this.level = level;
      this.message = message;
      this.keyValueMap = keyValueMap;
    }

    @Override
    public String toString() {
      return "TestData{" + "level='" + level + '\'' + ", message='" + message + '\''
          + ", keyValueMap=" + keyValueMap + '}';
    }
  }

  private static Stream<TestDataKVMap> provideTestDataKVMap() {
    Map<String, Object> mapWithNullValue = new HashMap<>();
    mapWithNullValue.put("key3", null); // Null value is allowed

    return Stream.of(
        new TestDataKVMap("trace", "Trace message",
            new PulseLoggerKeyValueMap(Map.of("key1", "value1"))),
        new TestDataKVMap("debug", "Debug message",
            new PulseLoggerKeyValueMap(Map.of("key2", "value2"))),
        new TestDataKVMap("info", "Info message",
            new PulseLoggerKeyValueMap(Map.of("key3", "value3"))),
        new TestDataKVMap("info", "Info message", new PulseLoggerKeyValueMap(Map.of())),
        new TestDataKVMap("warn", "Warn message",
            new PulseLoggerKeyValueMap(Map.of("key4", "value4"))),
        new TestDataKVMap("warn", "Warn message", null),
        new TestDataKVMap("error", "Error message",
            new PulseLoggerKeyValueMap(Map.of("key5", "value5"))),
        new TestDataKVMap("error", "Error message", new PulseLoggerKeyValueMap(mapWithNullValue)));
  }
}
