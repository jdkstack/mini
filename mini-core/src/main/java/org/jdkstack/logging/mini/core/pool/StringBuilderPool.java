package org.jdkstack.logging.mini.core.pool;

import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.logging.mini.core.ringbuffer.StringBuilderRingBuffer;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public final class StringBuilderPool {

  /** . */
  private static final RingBuffer<StringBuilder> BUFFER =
      new StringBuilderRingBuffer(Constants.CAPACITY);

  private StringBuilderPool() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder poll() {
    return BUFFER.poll();
  }
}
