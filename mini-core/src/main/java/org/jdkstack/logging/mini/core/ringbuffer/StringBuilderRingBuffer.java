package org.jdkstack.logging.mini.core.ringbuffer;

import org.jdkstack.jdkringbuffer.core.Power2;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;

/**
 * StringBuilder环形数组，用来缓存StringBuilder.
 *
 * <p>每次从Object[]循环获取一个StringBuilder。
 *
 * @author admin
 */
public class StringBuilderRingBuffer implements RingBuffer<StringBuilder> {

  /** . */
  private final Object[] rb;
  /** . */
  private int current;
  /** . */
  private final int mask;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity .
   * @author admin
   */
  public StringBuilderRingBuffer(final int capacity) {
    final int size = Power2.power2(capacity);
    this.mask = size - 1;
    this.rb = new Object[size];
    for (int i = 0; i < size; i++) {
      this.rb[i] = new StringBuilder(Constants.CAPACITY);
    }
  }

  @Override
  public final StringBuilder poll() {
    this.current++;
    final StringBuilder result = (StringBuilder) this.rb[this.mask & this.current];
    result.setLength(0);
    return result;
  }
}
