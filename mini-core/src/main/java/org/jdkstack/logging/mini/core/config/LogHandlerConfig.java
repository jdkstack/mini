package org.jdkstack.logging.mini.core.config;

import org.jdkstack.logging.mini.api.config.HandlerConfig;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @param <E> .
 * @author admin
 */
public class LogHandlerConfig implements HandlerConfig {

  /**
   * .
   */
  private final String directory = "logs";
  /**
   * .
   */
  private final String encoding = "UTF-8";
  /**
   * .
   */
  private final String type = "line";
  /**
   * .
   */
  private final String formatter = "logJsonFormatter";
  /**
   * .
   */
  private String prefix = "default";
  /**
   * .
   */
  private String name = "default";
  /**
   * .
   */
  private String fileName = "default";

  /**
   * .
   */
  private String fileNameExt = ".log";
  /**
   * .
   */
  private String handlerConsumeFilter = "handlerConsumeFilter";

  @Override
  public String getFileNameExt() {
    return this.fileNameExt;
  }

  @Override
  public void setFileNameExt(final String fileNameExt) {
    this.fileNameExt = fileNameExt;
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }

  @Override
  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String getHandlerConsumeFilter() {
    return this.handlerConsumeFilter;
  }

  @Override
  public void setHandlerConsumeFilter(final String handlerConsumeFilter) {
    this.handlerConsumeFilter = handlerConsumeFilter;
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
  public final String getDirectory() {
    return this.directory;
  }

  @Override
  public final String getPrefix() {
    return this.prefix;
  }

  @Override
  public final void setPrefix(String prefix) {
    this.prefix = prefix;
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
  public final String getFormatter() {
    return this.formatter;
  }
}
