package org.jdkstack.logging.mini.api.recorder;

import org.jdkstack.logging.mini.api.config.RecorderConfig;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Recorder {

  RecorderConfig getRecorderConfig();

  void setRecorderConfig(RecorderConfig recorderConfig);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @author admin
   */
  void log(String logLevel, String datetime, String message, Throwable thrown);

  void log(String logLevel, String message);

  void log(String logLevel, String message, Object arg1);

  void log(String logLevel, String message, Object arg1, Object arg2);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @author admin
   */
  void log(String logLevel, String message, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @author admin
   */
  void log(String logLevel, String datetime, String message);

  void log(String logLevel, String datetime, String message, Object arg1);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9);

  void log(String logLevel, String datetime, String message, Object arg1, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Throwable thrown);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @param logLevel .
   * @param message  .
   * @author admin
   */
  void process(final String logLevel, final String name, final String dateTime, final String message, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown);
}
