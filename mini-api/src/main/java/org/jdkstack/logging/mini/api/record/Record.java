package org.jdkstack.logging.mini.api.record;

import java.util.Map;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Record {

  int getLevelValue();

  void setLevelValue(int levelValue);

  Object[] getParams();

  void setParams(Object params, int index);

  int[] getPaths();

  void setPaths(int path, int index);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thrown .
   * @author admin
   */
  void setThrown(Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String 日志级别名称.
   * @author admin
   */
  String getLevelName();


  void setLevelName(String logLevel);

  StringBuilder getEvent();


  Throwable getThrowable();

  StringBuilder getMessageText();

  void setMessageText(String message);

  void clear();

  String getName();

  void setName(String name);

  Map<String, Object> getMap();
}
