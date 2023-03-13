package org.jdkstack.logging.mini.core.ringbuffer;

import java.util.Arrays;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.ringbuffer.core.Power2;

public class CharArrayRingBuffer implements RingBuffer<char[]> {

  /** . */
  private final char[][] rb;
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
  public CharArrayRingBuffer(final int capacity) {
    final int size = Power2.power2(capacity);
    this.mask = size - 1;
    this.rb = new char[size][];
    final String lineSeparator = System.lineSeparator();

    for (int i = 0; i < size; i++) {
      this.rb[i] = new char[160 + lineSeparator.length()];
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
