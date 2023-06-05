package org.jdkstack.pool.core;

import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ProducerWorker implements Runnable {

  private MpmcBlockingQueueV3<Record> queue;
  private Handler handler;
  private String logLevel;
  private String datetime;
  private CharBuffer message;
  private String className;
  private Throwable thrown;

  @Override
  public void run() {
    // 预生产(从循环队列tail取一个元素对象地址).
    final Record lr = this.queue.tail();
    // 为元素对象生产数据.
    handler.produce(logLevel, datetime, message, className, thrown, lr);
    this.queue.start();
  }

  public void setQueue(final MpmcBlockingQueueV3<Record> queue) {
    this.queue = queue;
  }

  public void setHandler(final Handler handler) {
    this.handler = handler;
  }

  public void setLogLevel(final String logLevel) {
    this.logLevel = logLevel;
  }

  public void setDatetime(final String datetime) {
    this.datetime = datetime;
  }

  public void setMessage(final CharBuffer message) {
    this.message = message;
  }

  public void setClassName(final String className) {
    this.className = className;
  }

  public void setThrown(final Throwable thrown) {
    this.thrown = thrown;
  }
}
