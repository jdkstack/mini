package org.jdkstack.logging.mini.core.config;

import org.jdkstack.logging.mini.api.config.LevelConfig;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @param <E> .
 * @author admin
 */
public class LogLevelConfig implements LevelConfig {

  /**
   * .
   */
  private String name;

  /**
   * .
   */
  private String value;

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  @Override
  public void setValue(final String value) {
    this.value = value;
  }
}
