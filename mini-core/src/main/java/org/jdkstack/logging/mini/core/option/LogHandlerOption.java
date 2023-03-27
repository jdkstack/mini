package org.jdkstack.logging.mini.core.option;

import org.jdkstack.logging.mini.api.option.HandlerOption;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class LogHandlerOption implements HandlerOption {

  /** . */
  private String name = Constants.DEFAULT;
  /** . */
  private String level = "MAX";
  /** 随机一个目录. */
  private String directory = "logs" + System.currentTimeMillis();
  /** . */
  private String prefix = Constants.DEFAULT;
  /** . */
  private String encoding = "UTF-8";
  /** line,size,second,minute,hour,day. */
  private String type = "size";
  /** . */
  private String className = "org.jdkstack.logging.mini.core.handler.FileHandlerV2";
  /** . */
  private String minLevel = "MIN";
  /** . */
  private String maxLevel = "MAX";
  /** . */
  private String formatter = "logJsonFormatter";
  /** . */
  private String filter = "logFilter";
  /** . */
  private String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
  /** . */
  private String interval = "1";
  /** . */
  private String intervalFormatter = "yyyyMMddHHmm";
  /** . */
  private String batchSize = "1";
  /** . */
  private String capacity = "1024";

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogHandlerOption() {
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
  public final String getLevel() {
    return this.level;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param level .
   * @author admin
   */
  @Override
  public final void setLevel(final String level) {
    this.level = level;
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
  public String getDirectory() {
    return this.directory;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param directory .
   * @author admin
   */
  @Override
  public void setDirectory(final String directory) {
    this.directory = directory;
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
  public String getPrefix() {
    return this.prefix;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix .
   * @author admin
   */
  @Override
  public void setPrefix(final String prefix) {
    this.prefix = prefix;
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
  public final String getEncoding() {
    return this.encoding;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param encoding .
   * @author admin
   */
  @Override
  public final void setEncoding(final String encoding) {
    this.encoding = encoding;
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
  public final String getFormatter() {
    return this.formatter;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param formatter .
   * @author admin
   */
  @Override
  public final void setFormatter(final String formatter) {
    this.formatter = formatter;
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
  public final String getDateTimeFormat() {
    return this.dateTimeFormat;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTimeFormat .
   * @author admin
   */
  @Override
  public final void setDateTimeFormat(final String dateTimeFormat) {
    this.dateTimeFormat = dateTimeFormat;
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
  public final String getInterval() {
    return this.interval;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param interval .
   * @author admin
   */
  @Override
  public final void setInterval(final String interval) {
    this.interval = interval;
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
  public final String getIntervalFormatter() {
    return this.intervalFormatter;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param intervalFormatter .
   * @author admin
   */
  @Override
  public final void setIntervalFormatter(final String intervalFormatter) {
    this.intervalFormatter = intervalFormatter;
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
  public final String getClassName() {
    return this.className;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param className .
   * @author admin
   */
  @Override
  public final void setClassName(final String className) {
    this.className = className;
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
  public final String getBatchSize() {
    return this.batchSize;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param batchSize .
   * @author admin
   */
  @Override
  public final void setBatchSize(final String batchSize) {
    this.batchSize = batchSize;
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
  public final String getCapacity() {
    return this.capacity;
  }


  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity .
   * @author admin
   */
  @Override
  public final void setCapacity(final String capacity) {
    this.capacity = capacity;
  }
}
