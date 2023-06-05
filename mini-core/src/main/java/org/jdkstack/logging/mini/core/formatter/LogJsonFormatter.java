package org.jdkstack.logging.mini.core.formatter;

import java.nio.Buffer;
import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.codec.Constants;

/**
 * 日志记录对象Record转成Json格式.
 *
 * <p>按行输出纯json格式的消息.
 *
 * @author admin
 */
public final class LogJsonFormatter implements Formatter {
  /** 临时数组. */
  private static final CharBuffer CHARBUF = CharBuffer.allocate(Constants.SOURCEN8);
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
  public Buffer format(final Record logRecord) {
    // json格式的日志消息.
    CHARBUF.clear();
    // json字符串开始.
    CHARBUF.append('{');
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    // json字符串结束.
    CHARBUF.append('}').append(lineSeparator);
    CHARBUF.flip();
    return CHARBUF;
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
    // 日志日期时间.
    CHARBUF.append('"');
    CHARBUF.append("dateTime");
    CHARBUF.append('"');
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getEvent());
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"levelName\"");
    CHARBUF.append(':');
    // 日志级别.
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getLevelName());
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"threadName\"");
    CHARBUF.append(':');
    // 线程名.
    CHARBUF.append('"');
    CHARBUF.append(Thread.currentThread().getName());
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"className\"");
    CHARBUF.append(':');
    // 类.
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getClassName());
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"message\"");
    CHARBUF.append(':');
    // 日志对象中的消息字段.
    CHARBUF.append('"');
    final CharBuffer message = logRecord.getMessage();
    // 将数据写入缓存.
    CHARBUF.put(message);
    CHARBUF.append('"');
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      CHARBUF.append(',');
      CHARBUF.append("\"stacktrace\"");
      CHARBUF.append(':');
      CHARBUF.append('[');
      CHARBUF.append('"');
      CHARBUF.append(thrown.getClass().getName());
      CHARBUF.append(':');
      CHARBUF.append(thrown.getMessage());
      CHARBUF.append('"');
      CHARBUF.append(',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      final int length = stackTraceElements.length;
      for (int i = 0; i < length; i++) {
        CHARBUF.append(separator);
        final StackTraceElement stackTraceElement = stackTraceElements[i];
        CHARBUF.append('"');
        CHARBUF.append(stackTraceElement.getClassName());
        CHARBUF.append('.');
        CHARBUF.append(stackTraceElement.getMethodName());
        CHARBUF.append('(');
        CHARBUF.append(stackTraceElement.getFileName());
        CHARBUF.append(':');
        CHARBUF.append("stackTraceElement.getLineNumber()");
        CHARBUF.append(')');
        CHARBUF.append('"');
        separator = ",";
      }
      CHARBUF.append(']');
    }
  }
}
