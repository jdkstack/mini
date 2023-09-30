package org.jdkstack.logging.mini.core.config;

import org.jdkstack.logging.mini.api.config.RecorderConfig;

public class LogRecorderConfig implements RecorderConfig {
  /** . */
  private String name = "default";

  /** . */
  private final String level = "MAX";

  /** . */
  private final String directory = "logs";

  /** . */
  private final String prefix = "default";

  /** . */
  private final String encoding = "UTF-8";

  /** . */
  private final String type = "line";

  /** . */
  private final String minLevel = "MIN";

  /** . */
  private final String maxLevel = "MAX";

  /** . */
  private final String formatter = "logJsonFormatter";

  /** . */
  private final String filter = "logFilter";

  /** . */
  private String handlers = Constants.DEFAULT;

  @Override
  public final String getName() {
    return this.name;
  }

  @Override
  public final String getLevel() {
    return this.level;
  }

  @Override
  public final String getDirectory() {
    return this.directory;
  }

  @Override
  public final String getPrefix() {
    return this.prefix;
  }

  @Override
  public final String getEncoding() {
    return this.encoding;
  }

  @Override
  public final String getType() {
    return this.type;
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
  public final String getFormatter() {
    return this.formatter;
  }

  @Override
  public final String getFilter() {
    return this.filter;
  }

  @Override
  public final void setName(final String name) {
    this.name = name;
  }

  @Override
  public final String getHandlers() {
    return this.handlers;
  }

  @Override
  public final void setHandlers(final String handlers) {
    this.handlers = handlers;
  }
}
