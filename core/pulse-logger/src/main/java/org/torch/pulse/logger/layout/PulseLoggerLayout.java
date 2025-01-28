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
package org.torch.pulse.logger.layout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * A custom Log4J2 layout that uses an embedded PatternLayout internally: %d{ISO8601} [%t] %-5level
 * %logger{36} - %msg%n
 */
@Plugin(name = "PulseLoggerLayout", category = "Core", elementType = "layout", printObject = true)
@Order(50)
public class PulseLoggerLayout extends AbstractStringLayout {

  // Fixed pattern for log messages
  private static final String FIXED_PATTERN = "%d{ISO8601} [%t] %-5level %logger{36} - %msg%n";

  // Fixed charset
  private static final Charset FIXED_CHARSET = StandardCharsets.UTF_8;

  // The delegate pattern layout
  private final PatternLayout delegateLayout;

  protected PulseLoggerLayout(PatternLayout delegateLayout) {
    super(FIXED_CHARSET);
    this.delegateLayout = delegateLayout;
  }

  @Override
  public String toSerializable(LogEvent event) {
    return delegateLayout.toSerializable(event);
  }

  @PluginFactory
  public static PulseLoggerLayout createLayout(@PluginConfiguration Configuration config) {
    // Build the internal PatternLayout using the fixed pattern and charset
    PatternLayout patternLayout =
        PatternLayout.newBuilder()
            .withPattern(FIXED_PATTERN)
            .withConfiguration(config)
            .withCharset(FIXED_CHARSET)
            .build();

    return new PulseLoggerLayout(patternLayout);
  }
}
