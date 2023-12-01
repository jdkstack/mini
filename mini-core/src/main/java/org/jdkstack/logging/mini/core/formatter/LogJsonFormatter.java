package org.jdkstack.logging.mini.core.formatter;

import java.util.Map;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogThread;

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
    Map<String, String> map = logRecord.getMap();
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
    // hostName.
    json.append('"');
    json.append(',');
    json.append("\"hostName\"");
    json.append(':');
    json.append('"');
    json.append(map.get("hostName"));
    // appName.
    json.append('"');
    json.append(',');
    json.append("\"appName\"");
    json.append(':');
    json.append('"');
    json.append(map.get("appName"));
    // ip.
    json.append('"');
    json.append(',');
    json.append("\"ip\"");
    json.append(':');
    json.append('"');
    json.append(map.get("ip"));
    // port.
    json.append('"');
    json.append(',');
    json.append("\"port\"");
    json.append(':');
    json.append('"');
    json.append(map.get("port"));
    // 进程id.
    json.append('"');
    json.append(',');
    json.append("\"pid\"");
    json.append(':');
    json.append('"');
    json.append(map.get("pid"));
    // 线程名.
    json.append('"');
    json.append(',');
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
    // 日志对象中的异常堆栈信息.
    json.append('"');
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
