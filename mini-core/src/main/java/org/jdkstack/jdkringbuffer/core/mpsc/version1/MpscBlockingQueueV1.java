package org.jdkstack.jdkringbuffer.core.mpsc.version1;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV1;
import org.jdkstack.jdkringbuffer.core.Constants;
import org.jdkstack.jdkringbuffer.core.Power2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class MpscBlockingQueueV1<E> extends AbstractLockBlockingQueueV1<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public MpscBlockingQueueV1() {
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
  public MpscBlockingQueueV1(final int capacity) {
    super(Power2.power2(capacity));
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
    // 检查队列中是否有元素.   检查是否被其他线程修改,如果返回true,则没有修改,可以更新值.
    if (0 > headSeq || this.tail.get() > headSeq) {
      // poll计数.
      this.head.getAndIncrement();
      // 获取环形数组索引位置.
      final int bufferIndex = headSeq & this.index;
      // 根据索引位置获取元素.
      e = this.ringBuffer[bufferIndex];
      // 将索引位置设置为空.
      this.ringBuffer[bufferIndex] = null;
    }
    return e;
  }
}
