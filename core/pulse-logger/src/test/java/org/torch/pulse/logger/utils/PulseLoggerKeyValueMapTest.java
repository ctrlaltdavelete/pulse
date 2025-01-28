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
package org.torch.pulse.logger.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PulseLoggerKeyValueMapTest {

  @Test
  void testConstructorWithValidMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "value1");
    PulseLoggerKeyValueMap keyValueMap = new PulseLoggerKeyValueMap(map);
    assertEquals(map, keyValueMap.getKeyValueMap());
  }

  @Test
  void testConstructorWithNullMap() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> new PulseLoggerKeyValueMap(null));
    assertEquals("Key-value pairs cannot be null", exception.getMessage());
  }

  @Test
  void testAddMethod() {
    Map<String, Object> map = new HashMap<>();
    PulseLoggerKeyValueMap keyValueMap = new PulseLoggerKeyValueMap(map);
    keyValueMap.add("key1", "value1");
    assertEquals("value1", keyValueMap.getKeyValueMap().get("key1"));
  }

  @Test
  void testAddMethodWithNullValue() {
    Map<String, Object> map = new HashMap<>();
    PulseLoggerKeyValueMap keyValueMap = new PulseLoggerKeyValueMap(map);
    keyValueMap.add("key1", null);
    assertNull(keyValueMap.getKeyValueMap().get("key1"));
  }

  @Test
  void testGetKeyValueMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("key1", "value1");
    PulseLoggerKeyValueMap keyValueMap = new PulseLoggerKeyValueMap(map);
    assertEquals(map, keyValueMap.getKeyValueMap());
  }
}
