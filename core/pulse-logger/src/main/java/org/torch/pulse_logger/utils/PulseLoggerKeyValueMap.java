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
package org.torch.pulse_logger.utils;

import java.util.Map;

public record PulseLoggerKeyValueMap(Map<String, Object> keyValuePairs) {

  // Primary Constructor
  public PulseLoggerKeyValueMap {
    if (keyValuePairs == null) {
      throw new IllegalArgumentException("Key-value pairs cannot be null");
    }
    // keyValuePairs.forEach(this::validateKeyValue);
  }

  // Add Method with Null Checks
  public PulseLoggerKeyValueMap add(String key, Object value) {
    // keyValuePairs.forEach(this::validateKeyValue);
    keyValuePairs.put(key, value);
    return this;
  }

  // Accessor for Key-Value Pairs
  public Map<String, Object> getKeyValueMap() {
    return keyValuePairs();
  }
}
