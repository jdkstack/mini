package org.jdkstack.logging.mini.core.recorder;

import org.jdkstack.logging.mini.core.handler.SystemHandler;

/**
 * (内部使用).
 *
 * <p>.
 *
 * @author admin
 */
public class SystemLogRecorder {
  private static final SystemLogRecorder SYSTEM_LOG_RECORDER = new SystemLogRecorder("system");

  private final SystemHandler systemHandler = new SystemHandler("system");

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @author admin
   */
  public SystemLogRecorder(final String name) {
    //
  }

  public static SystemLogRecorder getSystemRecorder() {
    return SYSTEM_LOG_RECORDER;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9) {
    this.systemHandler.process(
        logLevel, "system", datetime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9,
        null);
  }

  public final void log(final String logLevel, final String message) {
    this.core(logLevel, null, message, null, null, null, null, null, null, null, null, null);
  }

  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }
}
