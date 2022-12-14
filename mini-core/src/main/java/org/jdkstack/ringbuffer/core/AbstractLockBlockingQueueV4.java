package org.jdkstack.ringbuffer.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
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
public abstract class AbstractLockBlockingQueueV4<E> extends AbstractBlockingQueue<E>
    implements BlockingQueue<E>, RingBufferBlockingQueue {

  /** 环形数组. */
  protected final Entry<E>[] buffer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  protected AbstractLockBlockingQueueV4(final EventFactory<E> eventFactory) {
    this(Constants.CAPACITY, eventFactory);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @param capacity 容量必须是2的幂次方,比如2,4,8,16,32.
   * @author admin
   */
  protected AbstractLockBlockingQueueV4(final int capacity, final EventFactory<E> eventFactory) {
    super(capacity, capacity - 1);
    this.buffer = new Entry[capacity];
    for (int i = 0; i < capacity; i++) {
      final Entry<E> entry = new Entry<>(i);
      entry.setEntry(eventFactory.newInstance());
      this.buffer[i] = entry;
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
    int tailSeq = this.tail.get();
    while (true) {
      final Entry<E> cell = this.buffer[tailSeq & this.index];
      final int seq = cell.getSeq();
      final int dif = seq - tailSeq;
      if (0 == dif) {
        if (this.tail.compareAndSet(tailSeq, tailSeq + 1)) {
          cell.setSeq(tailSeq + 1);
          return cell.getEntry();
        } else {
          LockSupport.parkNanos(1L);
        }
      } else if (0 > dif) {
        return null;
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
