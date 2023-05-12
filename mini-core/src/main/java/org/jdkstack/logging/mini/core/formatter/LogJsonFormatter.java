package org.jdkstack.logging.mini.core.formatter;

import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.pool.StringBuilderPool;

/**
 * 日志记录对象Record转成Json格式.
 *
 * <p>按行输出纯json格式的消息.
 *
 * @author admin
 */
public final class LogJsonFormatter implements Formatter {

  /** . */
  private String dateTimeFormat;

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
   * @param dateTimeFormat 日期格式.
   * @author admin
   */
  public LogJsonFormatter(final String dateTimeFormat) {
    this.dateTimeFormat = dateTimeFormat;
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
    // json格式的日志消息.
    final StringBuilder sb = StringBuilderPool.poll();
    // json字符串开始.
    sb.append('{');
    // 日志对象中的特殊字段.
    this.handle(sb, logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    // json字符串结束.
    sb.append('}').append(lineSeparator);
    return sb;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param sb .
   * @param logRecord .
   * @author admin
   */
  public void handle(final StringBuilder sb, final Record logRecord) {
    // 日志日期时间.
    sb.append('"');
    sb.append("dateTime");
    sb.append('"');
    sb.append(':');
    sb.append('"');
    sb.append(logRecord.getEvent());
    sb.append('"');
    sb.append(',');
    sb.append("\"levelName\"");
    sb.append(':');
    // 日志级别.
    sb.append('"');
    sb.append(logRecord.getLevelName());
    sb.append('"');
    sb.append(',');
    sb.append("\"threadName\"");
    sb.append(':');
    // 线程名.
    sb.append('"');
    sb.append(Thread.currentThread().getName());
    sb.append('"');
    sb.append(',');
    sb.append("\"className\"");
    sb.append(':');
    // 类.
    sb.append('"');
    sb.append(logRecord.getClassName());
    sb.append('"');
    sb.append(',');
    sb.append("\"classMethod\"");
    sb.append(':');
    // 方法.
    sb.append('"');
    sb.append(logRecord.getClassMethod());
    sb.append('"');
    sb.append(',');
    sb.append("\"lineNumber\"");
    sb.append(':');
    // 行号.
    sb.append(logRecord.getLineNumber());
    sb.append(',');
    sb.append("\"message\"");
    sb.append(':');
    // 日志对象中的消息字段.
    sb.append('"');
    sb.append(logRecord.getMessage());
    sb.append('"');
    // 日志对象中的异常堆栈信息.
    /*    final Throwable thrown = logRecord.getThrown();
    if (null != thrown) {
      sb.append(',');
      sb.append("\"stacktrace\"");
      sb.append(':');
      sb.append('[');
      sb.append('"');
      sb.append(thrown.getClass().getName());
      sb.append(':');
      sb.append(thrown.getMessage());
      sb.append('"');
      sb.append(',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      final int length = stackTraceElements.length;
      for (int i = 0; i < length; i++) {
        sb.append(separator);
        final StackTraceElement stackTraceElement = stackTraceElements[i];
        sb.append('"');
        sb.append(stackTraceElement.getClassName());
        sb.append('.');
        sb.append(stackTraceElement.getMethodName());
        sb.append('(');
        sb.append(stackTraceElement.getFileName());
        sb.append(':');
        sb.append(stackTraceElement.getLineNumber());
        sb.append(')');
        sb.append('"');
        separator = ",";
      }
      sb.append(']');
    }*/
  }
}
