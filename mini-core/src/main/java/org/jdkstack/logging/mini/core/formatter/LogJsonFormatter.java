package org.jdkstack.logging.mini.core.formatter;

import java.util.Map;
import java.util.Map.Entry;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogThread;
import org.jdkstack.logging.mini.core.tool.StringBuilderTool;

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
    final LogThread logThread = (LogThread) Thread.currentThread();
    StringBuilder json = logThread.getJson();
    // json格式的日志消息.
    json.setLength(0);
    // json字符串开始.
    json.append('{');
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    // json字符串结束.
    json.append('}').append(lineSeparator);
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
    final LogThread logThread = (LogThread) Thread.currentThread();
    StringBuilder json = logThread.getJson();
    Map<String, Object> map = logRecord.getMap();
    // 日志日期时间.
    json.append('"');
    json.append("dateTime");
    json.append('"');
    json.append(':');
    json.append('"');
    final StringBuilder dateTime = logRecord.getEvent();
    json.append(dateTime);
    // 日志级别.
    json.append('"');
    json.append(',');
    json.append("\"levelName\"");
    json.append(':');
    json.append('"');
    json.append(logRecord.getLevelName());
    json.append('"');
    for (Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      json.append(',');
      json.append("\"");
      json.append(key);
      json.append("\"");
      json.append(':');
      json.append('"');
      StringBuilderTool.unbox(json, value);
      json.append('"');
    }
    // 线程名.
    json.append("\"threadName\"");
    json.append(':');
    json.append('"');
    json.append(Thread.currentThread().getName());
    // 类.
    json.append('"');
    json.append(',');
    json.append("\"name\"");
    json.append(':');
    json.append('"');
    json.append(logRecord.getName());
    // 日志对象中的消息字段.
    json.append('"');
    json.append(',');
    json.append("\"message\"");
    json.append(':');
    json.append('"');
    json.append(logRecord.getMessageText());
    json.append('"');
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      json.append(',');
      json.append("\"stacktrace\"");
      json.append(':');
      json.append('[');
      json.append('"');
      json.append(thrown.getClass().getName());
      json.append(':');
      json.append(thrown.getMessage());
      json.append('"');
      json.append(']');
    }
  }
}
