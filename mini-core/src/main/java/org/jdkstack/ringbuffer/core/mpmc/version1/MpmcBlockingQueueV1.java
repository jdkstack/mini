package org.jdkstack.ringbuffer.core.mpmc.version1;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV1;
import org.jdkstack.ringbuffer.core.Constants;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class MpmcBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public MpmcBlockingQueueV1() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity capacity.
   * @author admin
   */
  public MpmcBlockingQueueV1(final int capacity) {
    super(Power2.power2(capacity));
  }
}
