package org.jdkstack.logging.mini.core.option;

import org.jdkstack.logging.mini.api.option.RecorderOption;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class LogRecorderOption implements RecorderOption {

  /** . */
  private String type = "class";
  /** . */
  private String name = Constants.DEFAULT;
  /** . */
  private String handlers = Constants.DEFAULT;
  /** . */
  private String minLevel = "MIN";
  /** . */
  private String maxLevel = "MAX";
  /** . */
  private String filter = "logFilter";

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogRecorderOption() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getType() {
    return this.type;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param type .
   * @author admin
   */
  @Override
  public final void setType(final String type) {
    this.type = type;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getName() {
    return this.name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  @Override
  public final void setName(final String name) {
    this.name = name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getHandlers() {
    return this.handlers;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlers .
   * @author admin
   */
  @Override
  public final void setHandlers(final String handlers) {
    this.handlers = handlers;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getMinLevel() {
    return this.minLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param minLevel .
   * @author admin
   */
  @Override
  public final void setMinLevel(final String minLevel) {
    this.minLevel = minLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getMaxLevel() {
    return this.maxLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param maxLevel .
   * @author admin
   */
  @Override
  public final void setMaxLevel(final String maxLevel) {
    this.maxLevel = maxLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getFilter() {
    return this.filter;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param filter .
   * @author admin
   */
  @Override
  public final void setFilter(final String filter) {
    this.filter = filter;
  }
}
