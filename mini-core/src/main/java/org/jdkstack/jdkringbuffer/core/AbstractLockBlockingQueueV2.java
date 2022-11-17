package org.jdkstack.jdkringbuffer.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.jdkstack.jdkringbuffer.api.RingBufferBlockingQueue;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @param <E> .
 * @author admin
 */
public abstract class AbstractLockBlockingQueueV2<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue {

  /** 环形数组. */
  protected final Entry<E>[] buffer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  protected AbstractLockBlockingQueueV2() {
    this(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity 容量必须是2的幂次方,比如2,4,8,16,32.
   * @author admin
   */
  protected AbstractLockBlockingQueueV2(final int capacity) {
    super(capacity, capacity - 1);
    this.buffer = new Entry[capacity];
    for (int i = 0; i < capacity; i++) {
      this.buffer[i] = new Entry<>(i);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param e e.
   * @return boolean e.
   * @author admin
   */
  @Override
  public boolean offer(final E e) {
    int tailSeq = this.tail.get();
    while (true) {
      final Entry<E> cell = this.buffer[tailSeq & this.index];
      final int seq = cell.getSeq();
      final int dif = seq - tailSeq;
      if (0 == dif) {
        if (this.tail.compareAndSet(tailSeq, tailSeq + 1)) {
          cell.setEntry(e);
          cell.setSeq(tailSeq + 1);
          return true;
        } else {
          LockSupport.parkNanos(1L);
        }
      } else if (0 > dif) {
        return false;
      } else {
        tailSeq = this.tail.get();
      }
    }
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
  public E poll() {
    int headSeq = this.head.get();
    while (true) {
      final Entry<E> cell = this.buffer[headSeq & this.index];
      final int seq = cell.getSeq();
      final int dif = seq - (headSeq + 1);
      if (0 == dif) {
        if (this.head.compareAndSet(headSeq, headSeq + 1)) {
          final E e = cell.getEntry();
          cell.setEntry(null);
          cell.setSeq(headSeq + this.index + 1);
          return e;
        } else {
          LockSupport.parkNanos(1L);
        }
      } else if (0 > dif) {
        return null;
      } else {
        headSeq = this.head.get();
      }
    }
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
  public final E poll(final long timeout, final TimeUnit unit) {
    throw new UnsupportedOperationException("未实现.");
  }

  @Override
  public final E peek() {
    return this.buffer[this.head.get() & this.index].getEntry();
  }

  @Override
  public final int size() {
    return Math.max(this.tail.get() - this.head.get(), 0);
  }

  @Override
  public final boolean isEmpty() {
    return this.head.get() == this.tail.get();
  }
}
