package org.jdkstack.jdkringbuffer.core.mpmc.version4;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV4;
import org.jdkstack.jdkringbuffer.core.EventFactory;
import org.jdkstack.jdkringbuffer.core.Power2;

/**
 * 多生产多消费MPMC阻塞队列.
 *
 * <p>线程安全处理使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class MpmcBlockingQueueV4<E> extends AbstractLockBlockingQueueV4<E> {

  public MpmcBlockingQueueV4(final EventFactory eventFactory) {
    super(eventFactory);
  }

  public MpmcBlockingQueueV4(final int capacity, final EventFactory eventFactory) {
    super(Power2.power2(capacity), eventFactory);
  }
}
