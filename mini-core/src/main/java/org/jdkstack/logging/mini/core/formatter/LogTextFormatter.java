package org.jdkstack.logging.mini.core.formatter;

import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.pool.StringBuilderPool;

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
    // 文本格式的日志消息.
    final StringBuilder sb = StringBuilderPool.poll();
    // 日志对象中的特殊字段.
    this.handle(sb, logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    sb.append(lineSeparator);
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
    sb.append(logRecord.getSb());
    sb.append(' ');
    // 日志级别.
    sb.append(logRecord.getLevelName());
    sb.append(' ');
    // 线程名.
    sb.append(Thread.currentThread().getName());
    sb.append(' ');
    // 类.
    sb.append(logRecord.getClassName());
    sb.append(' ');
    // 方法.
    sb.append(logRecord.getClassMethod());
    sb.append(' ');
    // 行号.
    sb.append(logRecord.getLineNumber());
    sb.append(' ');
    // 日志对象中的消息字段.
    sb.append(logRecord.getMessage());
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrown();
    if (null != thrown) {
      sb.append(' ');
      sb.append('[');
      sb.append(thrown.getClass().getName());
      sb.append(':');
      sb.append(thrown.getMessage());
      sb.append(',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      final int length = stackTraceElements.length;
      for (int i = 0; i < length; i++) {
        sb.append(separator);
        final StackTraceElement stackTraceElement = stackTraceElements[i];
        sb.append(stackTraceElement.getClassName());
        sb.append('.');
        sb.append(stackTraceElement.getMethodName());
        sb.append('(');
        sb.append(stackTraceElement.getFileName());
        sb.append(':');
        sb.append(stackTraceElement.getLineNumber());
        sb.append(')');
        separator = ",";
      }
      sb.append(']');
    }
  }
}
