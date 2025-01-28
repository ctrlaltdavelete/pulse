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

import org.apache.logging.log4j.LogManager;
import org.torch.pulse_logger.core.PulseLogger;
import org.torch.pulse_logger.core.PulseLoggingFrameworkAdapter;

public final class Log4J2LoggingAdapter implements PulseLoggingFrameworkAdapter {

  @Override
  public PulseLogger getLogger(Class<?> clazz) {
    return new Log4J2Logger(LogManager.getLogger(clazz));
  }

  @Override
  public PulseLogger getLogger(String name) {
    return new Log4J2Logger(LogManager.getLogger(name));
  }
}
