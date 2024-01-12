package org.jdkstack.logging.mini.api.record;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Record {

  StringBuilder getDatetime();

  void setThrowable(Throwable throwable);

  String getHostIp();

  void setHostIp(String hostIp);

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

  String getApplicationSoftwareId();

  void setApplicationSoftwareId(String applicationSoftwareId);

  String getApplicationSoftwareVersion();

  void setApplicationSoftwareVersion(String applicationSoftwareVersion);

  String getSystemSoftwareId();

  void setSystemSoftwareId(String systemSoftwareId);

  String getSystemSoftwareVersion();

  void setSystemSoftwareVersion(String systemSoftwareVersion);

  String getSystemSoftwareName();

  void setSystemSoftwareName(String systemSoftwareName);

  String getHardwareId();

  void setHardwareId(String hardwareId);

  String getHardwareVersion();

  void setHardwareVersion(String hardwareVersion);

  String getHardwareName();

  void setHardwareName(String hardwareName);

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

  String getApplicationSoftwareName();

  void setApplicationSoftwareName(String applicationSoftwareName);

  long getProcessId();

  void setProcessId(long processId);

  long getTimeZone();

  void setTimeZone(long timeZone);
}
