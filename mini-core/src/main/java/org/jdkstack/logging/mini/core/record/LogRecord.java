package org.jdkstack.logging.mini.core.record;

import java.util.Arrays;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * 日志记录，最终被格式化成字符串.
 *
 * <p>.
 *
 * @author admin
 */
public class LogRecord implements Record {

  /**
   * 格式化后日志消息.
   */
  private final StringBuilder messageText = new StringBuilder(1024);
  /**
   * 事件发生时的datetime.
   */
  private final StringBuilder datetime = new StringBuilder(100);
  /**
   * 参数列表.
   */
  private final Object[] params = new Object[9];
  /**
   * 参数的位置.
   */
  private final int[] paths = new int[9];
  /**
   * 日志记录器名.
   */
  private String name;
  /**
   * 日志记录器完全限定名.
   */
  private String className = "-";
  /**
   * 日志记录器方法名.
   */
  private String methodName = "-";
  /**
   * 日志记录器行号.
   */
  private int lineNumber = 0;
  /**
   * 日志记录器所在的文件.
   */
  private String fileName = "-";
  /**
   * 日志级别名.
   */
  private String levelName;
  /**
   * 日志级别值.
   */
  private int levelValue;

  /**
   * 名.
   */
  private String producerThreadName;
  /**
   * 值.
   */
  private long producerThreadValue;

  /**
   * 名.
   */
  private String consumerThreadName;
  /**
   * 值.
   */
  private int consumerThreadValue;
  /**
   * 日志异常.
   */
  private Throwable throwable;

  // diagnostic logs/audit logs
  private String logTypeName;
  private int logTypeValue;
  // 当前hostName
  private String hostName = "-";
  // 当前hostIp
  private String hostIp = "-";
  // 当前进程
  private long processId;
  // 当前时区
  private long timeZone;

  // Application Software events/System Software events
  private String eventTypeName;
  private int eventTypeValue;

  // 事件来源软件的哪个模块。
  private String eventSourceId = "-";
  private String eventSourceName = "-";
  private String eventSourceValue = "-";

  // 抖音/浏览器
  private String applicationSoftwareId = "-";
  private String applicationSoftwareVersion = "-";
  private String applicationSoftwareName = "-";

  // windows/android/linux/Xiaomi HyperOS
  private String systemSoftwareId = "-";
  private String systemSoftwareVersion = "-";
  private String systemSoftwareName = "-";

  // 硬件设备：桌面/移动
  private String hardwareId = "-";
  // R6615/14
  private String hardwareVersion = "-";
  // 例如：PowerEdge/小米
  private String hardwareName = "-";

  @Override
  public StringBuilder getDatetime() {
    return this.datetime;
  }

  @Override
  public void setThrowable(final Throwable throwable) {
    this.throwable = throwable;
  }

  @Override
  public String getHostIp() {
    return this.hostIp;
  }

  @Override
  public void setHostIp(final String hostIp) {
    this.hostIp = hostIp;
  }

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
  public String getApplicationSoftwareId() {
    return this.applicationSoftwareId;
  }

  @Override
  public void setApplicationSoftwareId(final String applicationSoftwareId) {
    this.applicationSoftwareId = applicationSoftwareId;
  }

  @Override
  public String getApplicationSoftwareVersion() {
    return this.applicationSoftwareVersion;
  }

  @Override
  public void setApplicationSoftwareVersion(final String applicationSoftwareVersion) {
    this.applicationSoftwareVersion = applicationSoftwareVersion;
  }

  @Override
  public String getSystemSoftwareId() {
    return this.systemSoftwareId;
  }

  @Override
  public void setSystemSoftwareId(final String systemSoftwareId) {
    this.systemSoftwareId = systemSoftwareId;
  }

  @Override
  public String getSystemSoftwareVersion() {
    return this.systemSoftwareVersion;
  }

  @Override
  public void setSystemSoftwareVersion(final String systemSoftwareVersion) {
    this.systemSoftwareVersion = systemSoftwareVersion;
  }

  @Override
  public String getSystemSoftwareName() {
    return this.systemSoftwareName;
  }

  @Override
  public void setSystemSoftwareName(final String systemSoftwareName) {
    this.systemSoftwareName = systemSoftwareName;
  }

  @Override
  public String getHardwareId() {
    return this.hardwareId;
  }

  @Override
  public void setHardwareId(final String hardwareId) {
    this.hardwareId = hardwareId;
  }

  @Override
  public String getHardwareVersion() {
    return this.hardwareVersion;
  }

  @Override
  public void setHardwareVersion(final String hardwareVersion) {
    this.hardwareVersion = hardwareVersion;
  }

  @Override
  public String getHardwareName() {
    return this.hardwareName;
  }

  @Override
  public void setHardwareName(final String hardwareName) {
    this.hardwareName = hardwareName;
  }

  @Override
  public int getLevelValue() {
    return this.levelValue;
  }

  @Override
  public void setLevelValue(final int levelValue) {
    this.levelValue = levelValue;
  }

  @Override
  public final Object[] getParams() {
    return this.params;
  }

  @Override
  public final void setParams(final Object params, final int index) {
    this.params[index] = params;
  }

  @Override
  public final int[] getPaths() {
    return this.paths;
  }

  @Override
  public final void setPaths(final int path, final int index) {
    this.paths[index] = path;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thrown .
   * @author admin
   */
  @Override
  public final void setThrown(final Throwable thrown) {
    this.throwable = thrown;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  @Override
  public final String getLevelName() {
    return this.levelName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @author admin
   */
  @Override
  public final void setLevelName(final String logLevel) {
    this.levelName = logLevel;
  }

  @Override
  public final StringBuilder getEvent() {
    return this.datetime;
  }

  @Override
  public final Throwable getThrowable() {
    return this.throwable;
  }

  @Override
  public final StringBuilder getMessageText() {
    return this.messageText;
  }

  @Override
  public final void setMessageText(String message) {
    this.messageText.append(message);
  }

  @Override
  public final void clear() {
    this.levelName = null;
    this.levelValue = 0;
    this.name = null;
    this.messageText.setLength(0);
    this.datetime.setLength(0);
    this.throwable = null;
    Arrays.fill(this.params, null);
    Arrays.fill(this.paths, 0);
    this.producerThreadName = null;
    this.producerThreadValue = 0;
    this.consumerThreadName = null;
    this.consumerThreadValue = 0;
  }

  @Override
  public String getProducerThreadName() {
    return this.producerThreadName;
  }

  @Override
  public void setProducerThreadName(final String producerThreadName) {
    this.producerThreadName = producerThreadName;
  }

  @Override
  public long getProducerThreadValue() {
    return this.producerThreadValue;
  }

  @Override
  public void setProducerThreadValue(final long producerThreadValue) {
    this.producerThreadValue = producerThreadValue;
  }

  @Override
  public String getConsumerThreadName() {
    return this.consumerThreadName;
  }

  @Override
  public void setConsumerThreadName(final String consumerThreadName) {
    this.consumerThreadName = consumerThreadName;
  }

  @Override
  public int getConsumerThreadValue() {
    return this.consumerThreadValue;
  }

  @Override
  public void setConsumerThreadValue(final int consumerThreadValue) {
    this.consumerThreadValue = consumerThreadValue;
  }

  @Override
  public String getLogTypeName() {
    return this.logTypeName;
  }

  @Override
  public void setLogTypeName(final String logTypeName) {
    this.logTypeName = logTypeName;
  }

  @Override
  public int getLogTypeValue() {
    return this.logTypeValue;
  }

  @Override
  public void setLogTypeValue(final int logTypeValue) {
    this.logTypeValue = logTypeValue;
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
  public String getApplicationSoftwareName() {
    return this.applicationSoftwareName;
  }

  @Override
  public void setApplicationSoftwareName(final String applicationName) {
    this.applicationSoftwareName = applicationName;
  }

  @Override
  public long getProcessId() {
    return this.processId;
  }

  @Override
  public void setProcessId(final long processId) {
    this.processId = processId;
  }

  @Override
  public long getTimeZone() {
    return this.timeZone;
  }

  @Override
  public void setTimeZone(final long timeZone) {
    this.timeZone = timeZone;
  }

  @Override
  public String getClassName() {
    return this.className;
  }

  @Override
  public void setClassName(final String className) {
    this.className = className;
  }

  @Override
  public String getMethodName() {
    return this.methodName;
  }

  @Override
  public void setMethodName(final String methodName) {
    this.methodName = methodName;
  }

  @Override
  public int getLineNumber() {
    return this.lineNumber;
  }

  @Override
  public void setLineNumber(final int lineNumber) {
    this.lineNumber = lineNumber;
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }

  @Override
  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }
}
