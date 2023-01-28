package org.jdkstack.ringbuffer.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e .
 */
public class Entry<E> {

  /** 元素序列号,代表数组的下标 . */
  private final AtomicInteger seq = new AtomicInteger(0);
  /** 元素,代表数组元素. */
  private E er;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param i i .
   */
  public Entry(final int i) {
    this.seq.set(i);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return E e.
   */
  public final E getEntry() {
    return this.er;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param entry e.
   */
  public final void setEntry(final E entry) {
    this.er = entry;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return int e.
   */
  public final int getSeq() {
    return this.seq.get();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param seq e.
   */
  public final void setSeq(final int seq) {
    this.seq.set(seq);
  }
}
