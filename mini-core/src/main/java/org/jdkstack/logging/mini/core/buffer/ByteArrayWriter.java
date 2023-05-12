package org.jdkstack.logging.mini.core.buffer;

import java.io.RandomAccessFile;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class ByteArrayWriter extends AbstractByteArrayWriter {

  /** . */
  protected RandomAccessFile randomAccessFile;

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
  public void writeToDestination(final byte[] bytes, final int offset, final int length)
      throws Exception {
    this.randomAccessFile.write(bytes, offset, length);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param obj obj.
   * @author admin
   */
  @Override
  public void setDestination(final Object obj) {
    this.randomAccessFile = (RandomAccessFile) obj;
  }
}
