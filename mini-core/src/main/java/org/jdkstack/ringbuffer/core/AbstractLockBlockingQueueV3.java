package org.jdkstack.ringbuffer.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import org.jdkstack.ringbuffer.api.RingBufferBlockingQueue;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @param <E> .
 * @author admin
 */
public abstract class AbstractLockBlockingQueueV3<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue<E> {

  /** 环形数组. */
  protected final E[] ringBuffer;
  /** 环形数组入队时,是否被其他线程抢先放入了值. */
  protected final AtomicInteger tailLock = new AtomicInteger(0);
  /** 环形数组出队时,是否被其他线程抢先获取了值. */
  protected final AtomicInteger headLock = new AtomicInteger(0);

  protected AbstractLockBlockingQueueV3(final EventFactory<E> eventFactory) {
    this(Constants.CAPACITY, eventFactory);
  }

  protected AbstractLockBlockingQueueV3(final int capacity, final EventFactory<E> eventFactory) {
    super(capacity, capacity - 1);
    // jdk 泛型数组,会有检查异常,但不影响什么,用unchecked关闭检查.
    this.ringBuffer = (E[]) new Object[capacity];
    final int length = this.ringBuffer.length;
    for (int i = 0; i < length; i++) {
      this.ringBuffer[i] = eventFactory.newInstance();
    }
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param e e .
   * @return boolean b.
   * @author admin
   */
  @Override
  public final boolean offer(final E e) {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  public E publish() {
    while (true) {
      // 环形数组一共设置的元素的总个数(自增+1).
      final int tailSeq = this.tail.get();
      final int queueStart = tailSeq - this.capacity;
      // 检查环形数组是否满了. 检查是否被其他线程修改.
      if (0 > queueStart || this.head.get() > queueStart) {
        if (this.tailLock.compareAndSet(tailSeq, tailSeq + 1)) {
          // 向环形数组设置元素,取模后向对应的下标设置元素.
          final int tailSlot = tailSeq & this.index;
          return this.ringBuffer[tailSlot];
        } else {
          // 如果被占用,暂停1nanos.
          // https://blogs.oracle.com/dave/lightweight-contention-
          // management-for-efficient-compare-and-swap-operations
          LockSupport.parkNanos(1L);
        }
      } else {
        return null;
      }
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public final void start() {
    this.tail.getAndIncrement();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public final void end() {
    // poll计数.
    this.head.getAndIncrement();
  }

  /**
   * 阻塞方法消费元素.
   *
   * <p>Another description after blank line.
   *
   * @return boolean e.
   * @author admin
   */
  public final E head() {
    // 循环从环形数组中获取元素,直到能获取到为止.
    while (true) {
      // 拿到一个元素.
      final E e = this.poll();
      // 如果元素不为空,返回元素.
      if (null != e) {
        return e;
      } else {
        // 如果不能获取,则等待.
        this.emptyAwait();
      }
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  public final E tail() {
    E publish = this.publish();

    while (null == publish) {
      publish = this.publish();
      this.fullAwait();
    }

    return publish;
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
  public E poll() {
    while (true) {
      final int headSeq = this.head.get();
      // 检查队列中是否有元素.   检查是否被其他线程修改,如果返回true,则没有修改,可以更新值.
      if (0 > headSeq || this.tail.get() > headSeq) {
        if (this.headLock.compareAndSet(headSeq, headSeq + 1)) {
          // 获取环形数组索引位置.
          final int bufferIndex = headSeq & this.index;
          // 根据索引位置获取元素.
          return this.ringBuffer[bufferIndex];
        } else {
          LockSupport.parkNanos(1L);
        }
      } else {
        return null;
      }
    }
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @return E e.
   * @author admin
   */
  @Override
  public final E poll(final long timeout, final TimeUnit unit) {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @return E e.
   * @author admin
   */
  @Override
  public final E peek() {
    return this.ringBuffer[this.head.get() & this.index];
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @return int e.
   * @author admin
   */
  @Override
  public final int size() {
    return Math.max(this.tail.get() - this.head.get(), 0);
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean e.
   * @author admin
   */
  @Override
  public final boolean isEmpty() {
    return this.tail.get() == this.head.get();
  }
}
