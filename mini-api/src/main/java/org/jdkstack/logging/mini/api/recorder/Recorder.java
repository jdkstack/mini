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
  void log(final Throwable thrown, String logLevel, String datetime, String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message .
   * @author admin
   */
  void log(final Throwable thrown, String logLevel, String message);

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

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message .
   * @author admin
   */
  void log(String logLevel, String message);
}
