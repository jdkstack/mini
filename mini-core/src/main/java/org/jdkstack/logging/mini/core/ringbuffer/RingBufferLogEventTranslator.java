package org.jdkstack.logging.mini.core.ringbuffer;

import com.lmax.disruptor.EventTranslator;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class RingBufferLogEventTranslator implements EventTranslator<Record> {

  private LogRecorderContext context;

  /**
   * 日志级别.
   */
  private String logLevel;

  /**
   * 日志消息.
   */
  private String message;

  /**
   * 日志类.
   */
  private String name;

  /**
   * 日志异常.
   */
  private Throwable throwable;

  /**
   * 参数.
   */
  private Object args1;
  private Object args2;
  private Object args3;
  private Object args4;
  private Object args5;
  private Object args6;
  private Object args7;
  private Object args8;
  private Object args9;

  /**
   * 日志日期时间.
   */
  private String dateTime;

  public RingBufferLogEventTranslator(final LogRecorderContext context) {
    this.context = context;
  }

  @Override
  public void translateTo(Record lr, long sequence) {
    try {
      // 调用真正的生产逻辑。
      this.context.produce(logLevel, dateTime, message, name, args1, args2, args3, args4, args5, args6, args7, args8, args9, throwable, lr);
    } finally {
      this.clear();
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  public void process(final String logLevel, final String dateTime, final String message, final String name, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    this.logLevel = logLevel;
    this.message = message;
    this.dateTime = dateTime;
    this.name = name;
    this.args1 = arg1;
    this.args2 = arg2;
    this.args3 = arg3;
    this.args4 = arg4;
    this.args5 = arg5;
    this.args6 = arg6;
    this.args7 = arg7;
    this.args8 = arg8;
    this.args9 = arg9;
    this.throwable = thrown;
  }

  void clear() {
    this.process(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
  }
}
