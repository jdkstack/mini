package org.jdkstack.pool.core;

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
public class ConsumerWorker implements Runnable {

  private MpmcBlockingQueueV3<Record> queue;
  private Handler handler;

  @Override
  public void run() {
    // 预消费(从循环队列head取一个元素对象).
    final Record logRecord = this.queue.head();
    //handler.consume(logRecord);
    this.queue.end();
  }

  public void setQueue(final MpmcBlockingQueueV3<Record> queue) {
    this.queue = queue;
  }

  public void setHandler(final Handler handler) {
    this.handler = handler;
  }
}
