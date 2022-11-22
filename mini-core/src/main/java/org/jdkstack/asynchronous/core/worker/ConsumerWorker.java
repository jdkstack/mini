package org.jdkstack.asynchronous.core.worker;

import org.jdkstack.asynchronous.api.worker.ConWork;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.queue.Queue;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ConsumerWorker implements ConWork<Handler> {

  /** 从哪一个队列消费数据. */
  private Queue<Record> queue;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param queue 放入队列中的元素.
   * @author admin
   */
  public ConsumerWorker() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param event .
   * @author admin
   */
  @Override
  public final void handle(final Handler event) {
    // 非阻塞方法获取队列元素.
    final Record logRecord = this.queue.take();
    event.process(logRecord);
    this.queue.end();
  }

  public void setQueue(final Queue<Record> queue) {
    this.queue = queue;
  }
}
