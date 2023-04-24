package org.jdkstack.logging.mini.core.ringbuffer;

import java.util.Arrays;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.ringbuffer.core.Power2;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class CharArrayRingBuffer implements RingBuffer<char[]> {

  /** . */
  private final char[][] rb;
  /** . */
  private final int mask;
  /** . */
  private int current;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity .
   * @author admin
   */
  public CharArrayRingBuffer(final int capacity) {
    final int size = Power2.power2(capacity);
    this.mask = size - 1;
    this.rb = new char[size][];
    for (int i = 0; i < size; i++) {
      // 容量 2048 ,直接写入会导致写入大量的空.
      this.rb[i] = new char[Constants.CAPACITY];
    }
  }

  @Override
  public final char[] poll() {
    this.current++;
    final char[] result = this.rb[this.mask & this.current];
    Arrays.fill(result, (char) 0);
    return result;
  }
}
