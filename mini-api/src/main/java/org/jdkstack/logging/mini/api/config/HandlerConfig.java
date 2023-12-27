package org.jdkstack.logging.mini.api.config;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface HandlerConfig {

  String getFileNameExt();

  void setFileNameExt(String fileNameExt);

  String getFileName();

  void setFileName(String fileName);

  String getHandlerConsumeFilter();

  void setHandlerConsumeFilter(String handlerConsumeFilter);

  String getName();

  void setName(String name);

  String getDirectory();

  String getPrefix();

  void setPrefix(String prefix);

  String getEncoding();

  String getType();

  String getFormatter();

  int getCapacity();

  void setCapacity(int capacity);

  String getMode();

  void setMode(String mode);
}
