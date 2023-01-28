package org.jdkstack.ringbuffer.core.spmc.version1;

import org.jdkstack.ringbuffer.core.AbstractLockBlockingQueueV1;
import org.jdkstack.ringbuffer.core.Constants;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * 单生产多消费SPMC阻塞队列.
 *
 * <p>消费线程安全处理使用CAS锁.
 *
 * @author admin
 * @param <E> e .
 */
public class SpmcBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public SpmcBlockingQueueV1() {
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
  public SpmcBlockingQueueV1(final int capacity) {
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
    // 检查环形数组是否满了. 检查是否被其他线程修改.
    if (0 > queueStart || this.head.get() > queueStart) {
      this.tail.getAndIncrement();
      // 向环形数组设置元素,取模后向对应的下标设置元素.
      final int tailSlot = tailSeq & this.index;
      this.ringBuffer[tailSlot] = e;
      flag = true;
    }
    return flag;
  }
}
