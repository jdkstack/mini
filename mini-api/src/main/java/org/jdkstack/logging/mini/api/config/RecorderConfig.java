package org.jdkstack.logging.mini.api.config;

public interface RecorderConfig {

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
