/**
 * Module for PulseLogger, a lightweight logging abstraction.
 *
 * <p>Exports: - org.torch.pulse_logger.core: Public API for logging. -
 * org.torch.pulse_logger.utils: Utilities for structured logging.
 *
 * <p>Requires: - org.apache.logging.log4j and org.slf4j for logging framework integrations.
 */
module org.torch.pulse_logger {
  exports org.torch.pulse_logger.core;
  exports org.torch.pulse_logger.utils;

  requires org.apache.logging.log4j;
  requires org.apache.logging.log4j.core;
  requires org.slf4j;

  opens org.torch.pulse_logger.layout to
      org.apache.logging.log4j.core;
}
