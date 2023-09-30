package org.jdkstack.logging.mini.core.ringbuffer;

import org.jdkstack.logging.mini.api.ringbuffer.EventFactory;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class MpmcBlockingQueueV3<E> extends AbstractLockBlockingQueueV3<E> {

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public MpmcBlockingQueueV3(final EventFactory<E> eventFactory) {
    super(eventFactory);
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity .
   * @param eventFactory .
   * @author admin
   */
  public MpmcBlockingQueueV3(final int capacity, final EventFactory<E> eventFactory) {
    super(Power2.power2(capacity), eventFactory);
  }
}
