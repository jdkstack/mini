package org.jdkstack.logging.mini.api.config;

public interface ContextConfiguration {

  String getState();

  void setState(String state);

  int getRingBufferSize();

  void setRingBufferSize(int ringBufferSize);

  int getConsumers();

  void setConsumers(int consumers);

  String getHostName();

  void setHostName(String hostName);

  long getPid();

  void setPid(long pid);

  long getTimeZone();

  void setTimeZone(long timeZone);
}
