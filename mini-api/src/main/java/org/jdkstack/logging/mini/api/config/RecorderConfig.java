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

  String getMinLevel();

  String getMaxLevel();

  void setName(String name);

  String getHandlers();

  void setHandlers(String handlers);
}
