package org.jdkstack.ringbuffer.core.spsc.version3;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV3;
import org.jdkstack.ringbuffer.core.EventFactory;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <E> e.
 * @author admin
 */
public class SpscBlockingQueueV3<E> extends AbstractLockBlockingQueueV3<E> {

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public SpscBlockingQueueV3(final EventFactory<E> eventFactory) {
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
  public SpscBlockingQueueV3(final int capacity, final EventFactory<E> eventFactory) {
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

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return E e.
   * @author admin
   */
  @Override
  public final E poll() {
    final int headSeq = this.head.get();
    E e = null;
    // 检查队列中是否有元素.
    if (0 > headSeq || this.tail.get() > headSeq) {
      // 获取环形数组索引位置.
      final int bufferIndex = headSeq & this.index;
      // 根据索引位置获取元素.
      e = this.ringBuffer[bufferIndex];
      // poll计数.
      this.head.getAndIncrement();
    }
    return e;
  }
}
