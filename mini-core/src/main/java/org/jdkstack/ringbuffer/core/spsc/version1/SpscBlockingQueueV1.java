package org.jdkstack.ringbuffer.core.spsc.version1;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV1;
import org.jdkstack.ringbuffer.core.Constants;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class SpscBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public SpscBlockingQueueV1() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity capacity.
   */
  public SpscBlockingQueueV1(final int capacity) {
    super(Power2.power2(capacity));
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param e e .
   * @return boolean b.
   */
  @Override
  public final boolean offer(final E e) {
    // 环形数组一共设置的元素的总个数(自增+1).
    final int tailSeq = this.tail.get();
    final int queueStart = tailSeq - this.capacity;
    boolean flag = false;
    // 检查环形数组是否满了.
    if (0 > queueStart || this.head.get() > queueStart) {
      // 向环形数组设置元素,取模后向对应的下标设置元素.
      final int tailSlot = tailSeq & this.index;
      this.ringBuffer[tailSlot] = e;
      flag = true;
      this.tail.getAndIncrement();
    }
    return flag;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
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
      // 将索引位置设置为空.
      this.ringBuffer[bufferIndex] = null;
      // poll计数.
      this.head.getAndIncrement();
    }
    return e;
  }
}
