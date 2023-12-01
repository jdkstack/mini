package org.jdkstack.logging.mini.core.formatter;

import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogThread;

/**
 * 日志记录对象Record转成纯Text格式(空格分割).
 *
 * <p>按行输出纯文本格式的消息.
 *
 * @author admin
 */
public final class LogTextFormatter implements Formatter {

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
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogTextFormatter(final LogRecorderContext context) {
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
  public StringBuilder format(final Record logRecord) {
    final LogThread logThread = (LogThread) Thread.currentThread();
    StringBuilder text = logThread.getText();
    // 文本格式的日志消息.
    text.setLength(0);
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    text.append(lineSeparator);
    return text;
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
    StringBuilder text = logThread.getText();
    // 日志日期时间.
    final StringBuilder dateTime = logRecord.getEvent();
    text.append(dateTime);
    text.append(' ');
    // 日志级别.
    text.append(logRecord.getLevelName());
    text.append(' ');
    // 线程名.
    text.append(Thread.currentThread().getName());
    text.append(' ');
    // 类.
    text.append(logRecord.getName());
    text.append(' ');
    // 日志对象中的消息字段.
    final StringBuilder format = logRecord.getMessageText();
    text.append(format);
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      text.append(' ');
      text.append('[');
      text.append(thrown.getClass().getName());
      text.append(':');
      text.append(thrown.getMessage());
      text.append(']');
    }
  }
}
