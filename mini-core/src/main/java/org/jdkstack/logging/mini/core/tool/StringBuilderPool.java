package org.jdkstack.logging.mini.core.tool;

import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.logging.mini.core.ringbuffer.StringBuilderRingBuffer;

/**
 * boxing 装箱.
 *
 * <p>Make boxing explicit 明确装箱(基本类型+String,StringBuilder->Object).
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
   * @param value .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder box(final float value) {
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
  public static StringBuilder box(final double value) {
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
  public static StringBuilder box(final short value) {
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
  public static StringBuilder box(final int value) {
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
  public static StringBuilder box(final char value) {
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
  public static StringBuilder box(final long value) {
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
  public static StringBuilder box(final byte value) {
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
  public static StringBuilder box(final boolean value) {
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
  public static StringBuilder box(final String value) {
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
