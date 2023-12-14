package org.jdkstack.logging.mini.api.record;

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

  String getProducerThreadName();

  void setProducerThreadName(String producerThreadName);

  long getProducerThreadValue();

  void setProducerThreadValue(long producerThreadValue);

  String getConsumerThreadName();

  void setConsumerThreadName(String consumerThreadName);

  int getConsumerThreadValue();

  void setConsumerThreadValue(int consumerThreadValue);

  String getLogTypeName();

  void setLogTypeName(String logTypeName);

  int getLogTypeValue();

  void setLogTypeValue(int logTypeValue);

  String getHostName();

  void setHostName(String hostName);

  String getApplicationName();

  void setApplicationName(String applicationName);

  long getProcessId();

  void setProcessId(long processId);

  long getTimeZone();

  void setTimeZone(long timeZone);
}
