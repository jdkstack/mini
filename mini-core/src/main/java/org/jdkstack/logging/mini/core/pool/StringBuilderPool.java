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
  private static final RingBuffer<StringBuilder> BUFFER = new StringBuilderRingBuffer(Constants.CAPACITY);

  private StringBuilderPool() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final float value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final double value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final short value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final int value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final char value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final long value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final byte value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final boolean value) {
    return BUFFER.poll().append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder to(final String value) {
    return BUFFER.poll().append(value);
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
