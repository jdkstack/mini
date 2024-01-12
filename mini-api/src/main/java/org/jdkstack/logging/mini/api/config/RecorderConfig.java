package org.jdkstack.logging.mini.api.config;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface RecorderConfig {

  String getEventTypeName();

  void setEventTypeName(String eventTypeName);

  int getEventTypeValue();

  void setEventTypeValue(int eventTypeValue);

  String getEventSourceId();

  void setEventSourceId(String eventSourceId);

  String getEventSourceName();

  void setEventSourceName(String eventSourceName);

  String getEventSourceValue();

  void setEventSourceValue(String eventSourceValue);

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
