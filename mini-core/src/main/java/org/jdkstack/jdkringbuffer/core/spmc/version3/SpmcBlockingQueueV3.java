package org.jdkstack.jdkringbuffer.core.spmc.version3;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV3;
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
public class SpmcBlockingQueueV3<E> extends AbstractLockBlockingQueueV3<E> {


  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public SpmcBlockingQueueV3(final EventFactory eventFactory) {
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
  public SpmcBlockingQueueV3(final int capacity, final EventFactory eventFactory) {
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
    // 环形数组一共设置的元素的总个数(自增+1).
    final int tailSeq = this.tail.get();
    final int queueStart = tailSeq - this.capacity;
    E e = null;
    // 检查环形数组是否满了.
    if (0 > queueStart || this.head.get() > queueStart) {
      // 向环形数组设置元素,取模后向对应的下标设置元素.
      final int tailSlot = tailSeq & this.index;
      e = this.ringBuffer[tailSlot];
      this.tail.getAndIncrement();
    }
    return e;
  }
}
