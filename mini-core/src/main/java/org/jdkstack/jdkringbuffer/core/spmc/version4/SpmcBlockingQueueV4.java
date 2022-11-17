package org.jdkstack.jdkringbuffer.core.spmc.version4;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV4;
import org.jdkstack.jdkringbuffer.core.Entry;
import org.jdkstack.jdkringbuffer.core.EventFactory;
import org.jdkstack.jdkringbuffer.core.Power2;

/**
 * 单生产多消费SPMC阻塞队列.
 *
 * <p>消费线程安全处理使用CAS锁.
 *
 * @param <E> e .
 * @author admin
 */
public class SpmcBlockingQueueV4<E> extends AbstractLockBlockingQueueV4<E> {


  public SpmcBlockingQueueV4(final EventFactory eventFactory) {
    super(eventFactory);
  }

  public SpmcBlockingQueueV4(final int capacity, final EventFactory eventFactory) {
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
}
