package org.jdkstack.ringbuffer.core.spmc.version2;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV2;
import org.jdkstack.ringbuffer.core.Constants;
import org.jdkstack.ringbuffer.core.Entry;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * 单生产多消费SPMC阻塞队列.
 *
 * <p>消费线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public class SpmcBlockingQueueV2<E> extends AbstractLockBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public SpmcBlockingQueueV2() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  public SpmcBlockingQueueV2(final int capacity) {
    super(Power2.power2(capacity));
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param e e.
   * @return boolean e.
   */
  @Override
  public final boolean offer(final E e) {
    final int tailSeq = this.tail.get();
    final Entry<E> cell = this.buffer[tailSeq & this.index];
    final int seq = cell.getSeq();
    final int dif = seq - tailSeq;
    boolean flag = false;
    if (0 == dif) {
      cell.setEntry(e);
      cell.setSeq(tailSeq + 1);
      flag = true;
      this.tail.getAndIncrement();
    }
    return flag;
  }
}
