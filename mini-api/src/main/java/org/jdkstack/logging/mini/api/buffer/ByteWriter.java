package org.jdkstack.logging.mini.api.buffer;

import java.nio.ByteBuffer;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public interface ByteWriter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Byte .
   * @author admin
   */
  ByteBuffer getByteBuffer();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param buf .
   * @author admin
   */
  void flush(ByteBuffer buf);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void flush();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param bytes .
   * @param offset .
   * @param length .
   * @author admin
   */
  void writeToDestination(final byte[] bytes, final int offset, final int length);
}
