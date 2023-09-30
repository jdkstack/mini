package org.jdkstack.logging.mini.api.ringbuffer;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface RingBufferBlockingQueue<E> {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  boolean isFull();

  boolean offer(E e);

  E poll();

  E peek();

  int size();

  boolean isEmpty();
}
