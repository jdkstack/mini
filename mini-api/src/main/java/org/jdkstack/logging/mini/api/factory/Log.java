package org.jdkstack.logging.mini.api.factory;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Log {

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
}
