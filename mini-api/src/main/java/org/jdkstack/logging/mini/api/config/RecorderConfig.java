package org.jdkstack.logging.mini.api.config;

public interface RecorderConfig {

  String getFileName();

  void setFileName(String fileName);

  String getHandlerProduceFilter();

  void setHandlerProduceFilter(String handlerProduceFilter);

  String getHandlerConsumeFilter();

  void setHandlerConsumeFilter(String handlerConsumeFilter);

  String getName();

  String getLevel();

  String getDirectory();

  String getPrefix();

  String getEncoding();

  String getType();

  String getMinLevel();

  String getMaxLevel();

  String getFormatter();

  String getFilter();

  void setName(String name);

  String getHandlers();

  void setHandlers(String handlers);
  //
}
