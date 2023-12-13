package org.jdkstack.logging.mini.api.config;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface RecorderConfig {

  String getHandlerProduceFilter();

  void setHandlerProduceFilter(String handlerProduceFilter);

  String getName();

  void setName(String name);

  String getMinLevel();

  String getMaxLevel();

  String getHandlers();

  void setHandlers(String handlers);

  int getLogTypeValue();

  void setLogTypeValue(int logTypeValue);

  String getLogTypeName();

  void setLogTypeName(String logTypeName);
}
