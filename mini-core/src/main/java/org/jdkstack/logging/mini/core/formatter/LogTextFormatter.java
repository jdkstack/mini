package org.jdkstack.logging.mini.core.formatter;

import java.nio.Buffer;
import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;

/**
 * 日志记录对象Record转成纯Text格式(空格分割).
 *
 * <p>按行输出纯文本格式的消息.
 *
 * @author admin
 */
public final class LogTextFormatter implements Formatter {
  /** 临时数组. */
  private static final CharBuffer CHARBUF =
      CharBuffer.allocate(org.jdkstack.logging.mini.core.codec.Constants.SOURCEN8);
  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogTextFormatter() {
    //
  }

  /**
   * 日志记录对象Record转成纯Text格式(空格分割).
   *
   * <p>日志记录对象Record转成纯Text格式(空格分割).
   *
   * @param logRecord 日志记录对象.
   * @return 志记录转换成文本格式.
   * @author admin
   */
  @Override
  public Buffer format(final Record logRecord) {
    // 文本格式的日志消息.
    CHARBUF.clear();
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    CHARBUF.append(lineSeparator);
    CHARBUF.limit(CHARBUF.position());
    CHARBUF.position(0);
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
    String dateTime = logRecord.getEvent();
    if (dateTime != null) {
      CHARBUF.append(dateTime);
    } else {
      final long current = System.currentTimeMillis();
      StringBuilder encoder = DateTimeEncoder.encoder(current, 8 * 3600);
      int position = CHARBUF.position();
      encoder.getChars(0, encoder.length(), CHARBUF.array(), position);
      CHARBUF.position(position + encoder.length());
    }
    CHARBUF.append(' ');
    // 日志级别.
    CHARBUF.append(logRecord.getLevelName());
    CHARBUF.append(' ');
    // 线程名.
    CHARBUF.append(Thread.currentThread().getName());
    CHARBUF.append(' ');
    // 类.
    CHARBUF.append(logRecord.getClassName());
    CHARBUF.append(' ');
    // 日志对象中的消息字段.
    // final CharBuffer message = logRecord.getMessage();
    // CHARBUF.put(message.array(), message.arrayOffset(), message.remaining());
    final String message = logRecord.getMessage();
    int position = CHARBUF.position();
    StringBuilder format =
        LogFormatter.format(
            message,
            logRecord.getArgs1(),
            logRecord.getArgs2(),
            logRecord.getArgs3(),
            logRecord.getArgs4(),
            logRecord.getArgs5(),
            logRecord.getArgs6(),
            logRecord.getArgs7(),
            logRecord.getArgs8(),
            logRecord.getArgs9());
    format.getChars(0, format.length(), CHARBUF.array(), position);
    CHARBUF.position(position + format.length());
    // CHARBUF.limit(format.length());
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      CHARBUF.append(' ');
      CHARBUF.append('[');
      CHARBUF.append(thrown.getClass().getName());
      CHARBUF.append(':');
      CHARBUF.append(thrown.getMessage());
      CHARBUF.append(',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      final int length = stackTraceElements.length;
      for (int i = 0; i < length; i++) {
        CHARBUF.append(separator);
        final StackTraceElement stackTraceElement = stackTraceElements[i];
        CHARBUF.append(stackTraceElement.getClassName());
        CHARBUF.append('.');
        CHARBUF.append(stackTraceElement.getMethodName());
        CHARBUF.append('(');
        CHARBUF.append(stackTraceElement.getFileName());
        CHARBUF.append(':');
        CHARBUF.append("stackTraceElement.getLineNumber()");
        CHARBUF.append(')');
        separator = ",";
      }
      CHARBUF.append(']');
    }
  }
}
