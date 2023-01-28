package org.jdkstack.ringbuffer.core.mpsc.version3;

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
public class MpscBlockingQueueV3<E> extends AbstractLockBlockingQueueV3<E> {


  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public MpscBlockingQueueV3(final EventFactory<E> eventFactory) {
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
  public MpscBlockingQueueV3(final int capacity, final EventFactory<E> eventFactory) {
    super(Power2.power2(capacity), eventFactory);
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
    // 检查队列中是否有元素.   检查是否被其他线程修改,如果返回true,则没有修改,可以更新值.
    if (0 > headSeq || this.tail.get() > headSeq) {
      // poll计数.
      this.head.getAndIncrement();
      // 获取环形数组索引位置.
      final int bufferIndex = headSeq & this.index;
      // 根据索引位置获取元素.
      e = this.ringBuffer[bufferIndex];
    }
    return e;
  }
}
