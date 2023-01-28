package org.jdkstack.ringbuffer.core.spmc.version4;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV4;
import org.jdkstack.ringbuffer.core.Entry;
import org.jdkstack.ringbuffer.core.EventFactory;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * 单生产多消费SPMC阻塞队列.
 *
 * <p>消费线程安全处理使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class SpmcBlockingQueueV4<E> extends AbstractLockBlockingQueueV4<E> {

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public SpmcBlockingQueueV4(final EventFactory eventFactory) {
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
  public SpmcBlockingQueueV4(final int capacity, final EventFactory eventFactory) {
    super(Power2.power2(capacity), eventFactory);
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final E publish() {
    final int tailSeq = this.tail.get();
    final Entry<E> cell = this.buffer[tailSeq & this.index];
    final int seq = cell.getSeq();
    final int dif = seq - tailSeq;
    E e = null;
    if (0 == dif) {
      this.tail.getAndIncrement();
      e = cell.getEntry();
      cell.setSeq(tailSeq + 1);
    }
    return e;
  }
}
