package org.jdkstack.logging.mini.core.buffer;

import java.io.RandomAccessFile;

/**
 * .
 *
 * <p>将byte[]字节数组写入文件.
 *
 * @author admin
 */
public class ByteArrayWriter extends AbstractByteArrayWriter {

  protected long size;
  protected long line;
  /**
   * .
   */
  protected RandomAccessFile randomAccessFile;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param bytes  b.
   * @param offset o.
   * @param length l.
   * @author admin
   */
  @Override
  public void writeToDestination(final byte[] bytes, final int offset, final int length) throws Exception {
    this.size += length;
    this.line += 1;
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
    this.size = 0L;
    this.line = 0L;
    this.randomAccessFile = (RandomAccessFile) obj;
  }

  @Override
  public long getSize() {
    return this.size;
  }

  @Override
  public long getLine() {
    return this.line;
  }
}
