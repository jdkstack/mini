package org.jdkstack.logging.mini.core.handler;

import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.datetime.TimeZone;
import org.jdkstack.logging.mini.core.formatter.LogFormatterV2;
import org.jdkstack.logging.mini.core.thread.LogThread;

/**
 * This is a method description.
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {

  /**
   * .
   */
  protected final String key;
  protected final LogRecorderContext context;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public AbstractHandler(final LogRecorderContext context, final String key) {
    this.context = context;
    this.key = key;
  }

  /**
   * 从环形队列中获取元素对象.
   *
   * <p>将元素对象中的数据进行格式化,并写入缓存中,最后写入文件中.
   *
   * @param lr lr.
   * @author admin
   */
  @Override
  public void consume(final Record lr) throws Exception {
    final LogThread logThread = (LogThread) Thread.currentThread();
    ByteWriter destination3 = logThread.getDestination3();
    CharBuffer charBuf = logThread.getCharBuf();
    Encoder<CharBuffer> textEncoder = logThread.getTextEncoder();
    HandlerConfig value = this.context.getHandlerConfig(this.key);
    // 格式化日志对象.
    final StringBuilder logMessage = this.context.formatter(value.getFormatter(), lr);
    // 清除缓存.
    charBuf.clear();
    // 将数据写入缓存.
    final int position = charBuf.position();
    logMessage.getChars(0, logMessage.length(), charBuf.array(), position);
    charBuf.position(position + logMessage.length());
    charBuf.flip();
    // 切换规则.
    this.rules(lr, charBuf.remaining());
    // 开始编码.
    textEncoder.encode(charBuf, destination3);
    // 单条刷新到磁盘，速度最慢，但是数据丢失机率最小，批量速度最好，但是数据丢失机率最大，并且日志被缓存，延迟写入文件.
    this.flush();
  }

  @Override
  public void produce(final String logLevel, final String dateTime, final String message, final String name, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown, final Record lr) {
    // 设置日志级别.
    lr.setLevelName(logLevel);
    // 设置日志日期时间.
    final StringBuilder event = lr.getEvent();
    // 如果参数为空,使用系统当前的时间戳,the current time(UTC 8) in milliseconds.
    if (null == dateTime) {
      // 系统当前的时间戳.
      final long current = System.currentTimeMillis();
      // 使用固定时区+8:00(8小时x3600秒).
      DateTimeEncoder.encoder(event, current, TimeZone.EAST8);
    } else {
      // 不处理参数传递过来的日期时间.
      event.append(dateTime);
    }
    // 设置9个参数.
    lr.setParams(arg1, 0);
    lr.setParams(arg2, 1);
    lr.setParams(arg3, 2);
    lr.setParams(arg4, 3);
    lr.setParams(arg5, 4);
    lr.setParams(arg6, 5);
    lr.setParams(arg7, 6);
    lr.setParams(arg8, 7);
    lr.setParams(arg9, 8);
    // 用9个参数替换掉message中的占位符{}.
    LogFormatterV2.format(lr, message);
    // location中的className,method和lineNumber,暂时不处理.
    lr.setName(name);
    // 异常信息.
    lr.setThrown(thrown);
  }

  /**
   * 刷新一次IO.
   *
   * <p>.
   *
   * @author admin
   */
  public void flush() throws Exception {
    final LogThread logThread = (LogThread) Thread.currentThread();
    ByteWriter destination3 = logThread.getDestination3();
    destination3.flush();
  }

  /**
   * 切换文件的条件.
   *
   * <p>文件行数,文件大小,日期时间.
   *
   * @param lr     lr.
   * @param length length.
   * @author admin
   */
  public abstract void rules(final Record lr, final int length) throws Exception;
}
