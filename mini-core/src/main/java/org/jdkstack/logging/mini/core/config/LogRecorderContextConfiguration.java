package org.jdkstack.logging.mini.core.config;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.atomic.AtomicReference;
import org.jdkstack.logging.mini.api.config.ContextConfiguration;
import org.jdkstack.logging.mini.core.datetime.TimeZone;

public class LogRecorderContextConfiguration implements ContextConfiguration {

  private int ringBufferSize = 4096;
  private int consumers = 4;
  private String appName;
  private RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
  private String name = runtimeMXBean.getName();
  private long pid = Long.parseLong(name.split("@")[0]);
  private long timeZone = TimeZone.EAST8;
  // 当前hostName
  private String hostName;
  // 当前hostIp
  private String hostIp;
  // 当前进程
  private long processId;
  private AtomicReference<String> state = new AtomicReference<>("asynchronous");
  // 抖音/浏览器
  private String applicationSoftwareId;
  private String applicationSoftwareVersion;
  private String applicationSoftwareName;

  // windows/android/linux/Xiaomi HyperOS
  private String systemSoftwareId;
  private String systemSoftwareVersion;
  private String systemSoftwareName;

  // 硬件设备：桌面/移动
  private String hardwareId;
  // R6615/14
  private String hardwareVersion;
  // 例如：PowerEdge/小米
  private String hardwareName;

  @Override
  public String getState() {
    return this.state.get();
  }

  @Override
  public void setState(final String state) {
    this.state.set(state);
  }

  @Override
  public int getRingBufferSize() {
    return this.ringBufferSize;
  }

  @Override
  public void setRingBufferSize(final int ringBufferSize) {
    this.ringBufferSize = ringBufferSize;
  }

  @Override
  public int getConsumers() {
    return this.consumers;
  }

  @Override
  public void setConsumers(final int consumers) {
    this.consumers = consumers;
  }

  @Override
  public String getHostName() {
    return this.hostName;
  }

  @Override
  public void setHostName(final String hostName) {
    this.hostName = hostName;
  }

  @Override
  public String getAppName() {
    return this.appName;
  }

  @Override
  public void setAppName(final String appName) {
    this.appName = appName;
  }

  @Override
  public long getPid() {
    return this.pid;
  }

  @Override
  public void setPid(final long pid) {
    this.pid = pid;
  }

  @Override
  public long getTimeZone() {
    return this.timeZone;
  }

  @Override
  public void setTimeZone(final long timeZone) {
    this.timeZone = timeZone;
  }
}
