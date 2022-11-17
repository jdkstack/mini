package org.jdkstack.jdkringbuffer.core.spsc.version4;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV4;
import org.jdkstack.jdkringbuffer.core.Entry;
import org.jdkstack.jdkringbuffer.core.EventFactory;
import org.jdkstack.jdkringbuffer.core.Power2;

/**
 * 单生产单消费SPSC阻塞队列..
 *
 * <p>不需要处理线程安全,不使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class SpscBlockingQueueV4<E> extends AbstractLockBlockingQueueV4<E> {

  public SpscBlockingQueueV4(final EventFactory eventFactory) {
    super(eventFactory);
  }

  public SpscBlockingQueueV4(final int capacity, final EventFactory eventFactory) {
    super(Power2.power2(capacity), eventFactory);
  }

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

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean e.
   * @author admin
   */
  @Override
  public final E poll() {
    final int headSeq = this.head.get();
    final Entry<E> cell = this.buffer[headSeq & this.index];
    final int seq = cell.getSeq();
    final int dif = seq - (headSeq + 1);
    E e = null;
    if (0 == dif) {
      this.head.getAndIncrement();
      e = cell.getEntry();
      cell.setSeq(headSeq + this.index + 1);
    }
    return e;
  }
}
