package org.jdkstack.logging.mini.extension.buffer;

import java.nio.MappedByteBuffer;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class MmapByteArrayWriter extends ByteArrayWriter {
  /** . */
  private MappedByteBuffer mappedBuffer;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param bytes b.
   * @param offset o.
   * @param length l.
   * @author admin
   */
  @Override
  public final void writeToDestination(final byte[] bytes, final int offset, final int length)
      throws Exception {
    this.mappedBuffer.put(bytes, offset, length);
  }

  @Override
  public void setDestination(Object obj) {
    this.mappedBuffer = (MappedByteBuffer) obj;
  }
}
