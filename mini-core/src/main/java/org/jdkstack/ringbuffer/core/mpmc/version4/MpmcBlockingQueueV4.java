package org.jdkstack.ringbuffer.core.mpmc.version4;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV4;
import org.jdkstack.ringbuffer.core.EventFactory;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class MpmcBlockingQueueV4<E> extends AbstractLockBlockingQueueV4<E> {

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public MpmcBlockingQueueV4(final EventFactory<E> eventFactory) {
    super(eventFactory);
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity     .
   * @param eventFactory .
   * @author admin
   */
  public MpmcBlockingQueueV4(final int capacity, final EventFactory<E> eventFactory) {
    super(Power2.power2(capacity), eventFactory);
  }
}
