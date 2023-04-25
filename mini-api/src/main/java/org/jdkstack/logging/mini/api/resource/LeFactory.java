package org.jdkstack.logging.mini.api.resource;

import org.jdkstack.logging.mini.api.level.Level;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public interface LeFactory {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return Level .
   * @author admin
   */
  Level findLevel(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @param value .
   * @author admin
   */
  void putLevel(String name, int value);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevels .
   * @param maxLevel .
   * @param minLevel .
   * @return boolean .
   * @author admin
   */
  boolean doFilter(String logLevels, Level maxLevel, Level minLevel);
}
