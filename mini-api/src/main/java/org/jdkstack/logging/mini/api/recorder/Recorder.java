package org.jdkstack.logging.mini.api.recorder;

import org.jdkstack.logging.mini.api.level.Level;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Recorder {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getType();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key .
   * @param handler .
   * @author admin
   */
  void addHandlers(String key, String handler);

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @return String .
   * @author admin
   */
  String getHandler(String key);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevels logLevels.
   * @return boolean
   * @author admin
   */
  boolean doFilter(String logLevels);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler handler.
   * @author admin
   */
  void removeHandler(String handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  Level getMinLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param minLevel minLevel.
   * @author admin
   */
  void setMinLevel(Level minLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  Level getMaxLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param maxLevel maxLevel.
   * @author admin
   */
  void setMaxLevel(Level maxLevel);

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
  void log(String logLevel, String datetime, String message, Throwable thrown);

  void log(String logLevel, String message);

  void log(String logLevel, String message, Object arg1);

  void log(String logLevel, String message, Object arg1, Object arg2);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3);

  void log(String logLevel, String message, Object arg1, Object arg2, Object arg3, Object arg4);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8,
      Object arg9);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message .
   * @author admin
   */
  void log(String logLevel, String message, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Throwable thrown);

  void log(String logLevel, String message, Object arg1, Object arg2, Throwable thrown);

  void log(
      String logLevel, String message, Object arg1, Object arg2, Object arg3, Throwable thrown);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Throwable thrown);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Throwable thrown);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Throwable thrown);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Throwable thrown);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8,
      Throwable thrown);

  void log(
      String logLevel,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8,
      Object arg9,
      Throwable thrown);

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
  void log(String logLevel, String datetime, String message);

  void log(String logLevel, String datetime, String message, Object arg1);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2);

  void log(String logLevel, String datetime, String message, Object arg1, Object arg2, Object arg3);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8,
      Object arg9);

  void log(String logLevel, String datetime, String message, Object arg1, Throwable thrown);

  void log(
      String logLevel, String datetime, String message, Object arg1, Object arg2, Throwable thrown);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Throwable thrown);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Throwable thrown);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Throwable thrown);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Throwable thrown);

  void log(
      String logLevel,
      String datetime,
      String message,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Throwable thrown);
}
