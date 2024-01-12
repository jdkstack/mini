package org.jdkstack.logging.mini.core.config;

import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.core.level.EventType;
import org.jdkstack.logging.mini.core.level.LogType;

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
  // diagnostic logs/audit logs
  private String logTypeName = LogType.AL.getName();
  private int logTypeValue = LogType.AL.intValue();

  // Application Software events/System Software events
  private String eventTypeName = EventType.APE.getName();
  private int eventTypeValue = EventType.APE.intValue();

  // 事件来源软件的哪个模块。
  private String eventSourceId;
  private String eventSourceName;
  private String eventSourceValue;

  @Override
  public String getEventTypeName() {
    return this.eventTypeName;
  }

  @Override
  public void setEventTypeName(final String eventTypeName) {
    this.eventTypeName = eventTypeName;
  }

  @Override
  public int getEventTypeValue() {
    return this.eventTypeValue;
  }

  @Override
  public void setEventTypeValue(final int eventTypeValue) {
    this.eventTypeValue = eventTypeValue;
  }

  @Override
  public String getEventSourceId() {
    return this.eventSourceId;
  }

  @Override
  public void setEventSourceId(final String eventSourceId) {
    this.eventSourceId = eventSourceId;
  }

  @Override
  public String getEventSourceName() {
    return this.eventSourceName;
  }

  @Override
  public void setEventSourceName(final String eventSourceName) {
    this.eventSourceName = eventSourceName;
  }

  @Override
  public String getEventSourceValue() {
    return this.eventSourceValue;
  }

  @Override
  public void setEventSourceValue(final String eventSourceValue) {
    this.eventSourceValue = eventSourceValue;
  }

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
