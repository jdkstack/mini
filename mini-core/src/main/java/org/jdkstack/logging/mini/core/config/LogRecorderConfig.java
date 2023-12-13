package org.jdkstack.logging.mini.core.config;

import org.jdkstack.logging.mini.api.config.RecorderConfig;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @param <E> .
 * @author admin
 */
public class LogRecorderConfig implements RecorderConfig {

  /**
   * .
   */
  private final String minLevel = "MIN";
  /**
   * .
   */
  private final String maxLevel = "MAX";
  /**
   * .
   */
  private String name = "default";
  /**
   * .
   */
  private String handlers = Constants.DEFAULT;

  /**
   * .
   */
  private String handlerProduceFilter = "handlerProduceFilter";

  private String logTypeName;
  private int logTypeValue;

  @Override
  public String getHandlerProduceFilter() {
    return this.handlerProduceFilter;
  }

  @Override
  public void setHandlerProduceFilter(final String handlerProduceFilter) {
    this.handlerProduceFilter = handlerProduceFilter;
  }

  @Override
  public final String getName() {
    return this.name;
  }

  @Override
  public final void setName(final String name) {
    this.name = name;
  }

  @Override
  public final String getMinLevel() {
    return this.minLevel;
  }

  @Override
  public final String getMaxLevel() {
    return this.maxLevel;
  }

  @Override
  public final String getHandlers() {
    return this.handlers;
  }

  @Override
  public final void setHandlers(final String handlers) {
    this.handlers = handlers;
  }

  @Override
  public int getLogTypeValue() {
    return logTypeValue;
  }

  @Override
  public void setLogTypeValue(final int logTypeValue) {
    this.logTypeValue = logTypeValue;
  }

  @Override
  public String getLogTypeName() {
    return logTypeName;
  }

  @Override
  public void setLogTypeName(final String logTypeName) {
    this.logTypeName = logTypeName;
  }
}
