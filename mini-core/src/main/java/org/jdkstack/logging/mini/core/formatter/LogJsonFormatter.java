package org.jdkstack.logging.mini.core.formatter;

import java.nio.Buffer;
import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.codec.Constants;

/**
 * 日志记录对象Record转成Json格式.
 *
 * <p>按行输出纯json格式的消息.
 *
 *
 * @author admin
 */
public final class LogJsonFormatter implements Formatter {
  /** 临时数组. */
  private static final CharBuffer CHARBUF = CharBuffer.allocate(Constants.SOURCEN8);

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
    final StringBuilder dateTime = logRecord.getEvent();
    final int position = CHARBUF.position();
    dateTime.getChars(0, dateTime.length(), CHARBUF.array(), position);
    CHARBUF.position(position + dateTime.length());
    // 日志级别.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"levelName\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getLevelName());
    // hostName.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"hostName\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getHostName());
    // appName.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"appName\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getAppName());
    // ip.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"ip\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getIp());
    // port.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"port\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getPort());
    // 进程id.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"pid\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    final StringBuilder pid = logRecord.getPid();
    final int position2 = CHARBUF.position();
    pid.getChars(0, pid.length(), CHARBUF.array(), position2);
    CHARBUF.position(position2 + pid.length());
    // 线程名.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"threadName\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(Thread.currentThread().getName());
    // 类.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"name\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    CHARBUF.append(logRecord.getName());
    // 日志对象中的消息字段.
    CHARBUF.append('"');
    CHARBUF.append(',');
    CHARBUF.append("\"message\"");
    CHARBUF.append(':');
    CHARBUF.append('"');
    final StringBuilder format = logRecord.getMessageText();
    final int position1 = CHARBUF.position();
    format.getChars(0, format.length(), CHARBUF.array(), position1);
    CHARBUF.position(position1 + format.length());
    // 日志对象中的异常堆栈信息.
    CHARBUF.append('"');
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
        CHARBUF.append(stackTraceElement.getLineNumber() + "");
        CHARBUF.append(')');
        CHARBUF.append('"');
        separator = ",";
      }
      CHARBUF.append(']');
    }
  }
}
