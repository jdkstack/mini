package org.jdkstack.logging.mini.core.ringbuffer;

import java.util.Arrays;
import org.jdkstack.jdkringbuffer.core.Power2;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;

/**
 * byte[] 字节数组环形数组，用来缓存byte[]字节数组.
 *
 * <p>每次从Object[]循环获取一个byte[]字节数组。
 *
 * @author admin
 */
public class ByteArrayRingBuffer implements RingBuffer<byte[]> {

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
  public ByteArrayRingBuffer(final int capacity) {
    final int size = Power2.power2(capacity);
    this.mask = size - 1;
    this.rb = new Object[size];
    for (int i = 0; i < size; i++) {
      this.rb[i] = new byte[Constants.CAPACITY];
    }
  }

  @Override
  public final byte[] poll() {
    this.current++;
    final byte[] result = (byte[]) this.rb[this.mask & this.current];
    Arrays.fill(result, (byte) 0);
    return result;
  }
}
