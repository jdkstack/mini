package org.jdkstack.jdkringbuffer.core;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列核心.
 *
 * <p>Another description after blank line.
 *
 * @param <E> e.
 * @author admin
 */
public abstract class AbstractBlockingQueue<E> extends AbstractQueue<E> {

  /** 环形数组的容量. */
  protected final int capacity;
  /** 环形数组入队时,当前元素的坐标. */
  protected final int index;
  /** 环形数组出队时,总共出队了多少个元素. */
  protected final AtomicInteger head = new AtomicInteger(0);
  /** 环形数组入队时,总共入队了多少个元素. */
  protected final AtomicInteger tail = new AtomicInteger(0);

  protected AbstractBlockingQueue(final int capacity, final int index) {
    this.capacity = capacity;
    this.index = index;
  }

  /**
   * 阻塞方法消费元素.
   *
   * <p>Another description after blank line.
   *
   * @return boolean e.
   * @author admin
   */
  public E take() {
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
   * @author admin
   */
  public final void emptyAwait() {
    final Thread t = Thread.currentThread();
    while (this.isEmpty() && !t.isInterrupted()) {
      Thread.onSpinWait();
    }
  }

  /**
   * 阻塞方法放入元素.
   *
   * <p>Another description after blank line.
   *
   * @param e e.
   * @author admin
   */
  public final void put(final E e) {
    // 循环向环形数组插入数据,直到能插入为止.
    while (!this.offer(e)) {
      // 如果不能插入,则等待.
      this.fullAwait();
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public final void fullAwait() {
    final Thread t = Thread.currentThread();
    while (this.isFull() && !t.isInterrupted()) {
      Thread.onSpinWait();
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param e       e.
   * @param timeout t.
   * @param unit    u.
   * @return boolean e.
   * @author admin
   */
  public final boolean offer(final E e, final long timeout, final TimeUnit unit) {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean e.
   * @author admin
   */
  public final boolean isFull() {
    final int queueStart = this.tail.get() - this.capacity;
    return this.head.get() == queueStart;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Iterator E e.
   * @author admin
   */
  @Override
  public final Iterator<E> iterator() {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int e.
   * @author admin
   */
  public final int remainingCapacity() {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param c c.
   * @return int e.
   * @author admin
   */
  public final int drainTo(final Collection<? super E> c) {
    throw new UnsupportedOperationException("未实现.");
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param c           c.
   * @param maxElements maxElements.
   * @return int e.
   * @author admin
   */
  public final int drainTo(final Collection<? super E> c, final int maxElements) {
    throw new UnsupportedOperationException("未实现.");
  }
}
