package org.jdkstack.logging.mini.core.formatter;

import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.tool.ThreadLocalTool;

/**
 * 日志记录对象Record转成Json格式.
 *
 * <p>按行输出纯json格式的消息.
 *
 * @author admin
 */
public final class LogJsonFormatter implements Formatter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogJsonFormatter() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogJsonFormatter(final LogRecorderContext context) {
    //
  }

  /**
   * 日志记录对象Record转成Json格式.
   *
   * <p>日志记录对象Record转成Json格式.
   *
   * @param logRecord 日志记录对象.
   * @return 日志记录转换成json格式.
   * @author admin
   */
  @Override
  public StringBuilder format(final Record logRecord) {
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    StringBuilder json = logConsumeThread.getJson();
    // json格式的日志消息.
    json.setLength(0);
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    return json;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord .
   * @author admin
   */
  public void handle(final Record logRecord) {
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    StringBuilder json = logConsumeThread.getJson();
    // 日志日期时间.
    json.append("{\"dateTime\":\"");
    json.append(logRecord.getEvent());
    // 日志级别.
    json.append("\",");
    json.append("\"levelName\":\"");
    json.append(logRecord.getLevelName());
    json.append("\",");
    json.append("\"levelValue\":");
    json.append(logRecord.getLevelValue());
    json.append(',');
    json.append("\"logTypeName\":\"");
    json.append(logRecord.getLogTypeName());
    json.append("\",");
    json.append("\"logTypeValue\":\"");
    json.append(logRecord.getLogTypeValue());
    json.append("\",");
    json.append("\"host\":\"");
    json.append(logRecord.getHostName());
    json.append("\",");
    json.append("\"ip\":\"");
    json.append(logRecord.getHostIp());
    json.append("\",");
    json.append("\"pid\":\"");
    json.append(logRecord.getProcessId());
    json.append("\",");
    json.append("\"tz\":\"");
    json.append(logRecord.getTimeZone());
    json.append("\",");
    json.append("\"eventTypeName\":\"");
    json.append(logRecord.getEventTypeName());
    json.append("\",");
    json.append("\"eventTypeValue\":\"");
    json.append(logRecord.getEventTypeValue());
    json.append("\",");
    json.append("\"eventSourceId\":\"");
    json.append(logRecord.getEventSourceId());
    json.append("\",");
    json.append("\"eventSourceName\":\"");
    json.append(logRecord.getEventSourceName());
    json.append("\",");
    json.append("\"eventSourceValue\":\"");
    json.append(logRecord.getEventSourceValue());
    json.append("\",");
    json.append("\"applicationSoftwareId\":\"");
    json.append(logRecord.getApplicationSoftwareId());
    json.append("\",");
    json.append("\"applicationSoftwareVersion\":\"");
    json.append(logRecord.getApplicationSoftwareVersion());
    json.append("\",");
    json.append("\"applicationSoftwareName\":\"");
    json.append(logRecord.getApplicationSoftwareName());
    json.append("\",");
    json.append("\"systemSoftwareId\":\"");
    json.append(logRecord.getSystemSoftwareId());
    json.append("\",");
    json.append("\"systemSoftwareVersion\":\"");
    json.append(logRecord.getSystemSoftwareVersion());
    json.append("\",");
    json.append("\"systemSoftwareName\":\"");
    json.append(logRecord.getSystemSoftwareName());
    json.append("\",");
    json.append("\"hardwareId\":\"");
    json.append(logRecord.getHardwareId());
    json.append("\",");
    json.append("\"hardwareVersion\":\"");
    json.append(logRecord.getHardwareVersion());
    json.append("\",");
    json.append("\"hardwareName\":\"");
    json.append(logRecord.getHardwareName());
    json.append("\",");
    json.append("\"producerThreadName\":\"");
    json.append(logRecord.getProducerThreadName());
    json.append("\",");
    json.append("\"producerThreadValue\":\"");
    json.append(logRecord.getProducerThreadValue());
    json.append("\",");
    json.append("\"consumerThreadName\":\"");
    json.append(Thread.currentThread().getName());
    json.append("\",");
    json.append("\"consumerThreadValue\":\"");
    json.append(Thread.currentThread().getId());
    json.append("\",");
    json.append("\"name\":\"");
    json.append(logRecord.getName());
    json.append("\",");
    json.append("\"className\":\"");
    json.append(logRecord.getClassName());
    json.append("\",");
    json.append("\"methodName\":\"");
    json.append(logRecord.getMethodName());
    json.append("\",");
    json.append("\"lineNumber\":\"");
    json.append(logRecord.getLineNumber());
    json.append("\",");
    json.append("\"fileName\":\"");
    json.append(logRecord.getFileName());
    json.append("\",");
    json.append("\"message\":\"");
    json.append(logRecord.getMessageText());
    json.append('"');
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      json.append(",\"stacktrace\":[\"");
      json.append(thrown.getClass().getName());
      json.append(':');
      json.append(thrown.getMessage());
      json.append("\"]");
    }
    // 增加一个换行符号(按照平台获取)
    json.append('}').append(System.lineSeparator());
  }
}
