package org.jdkstack.logging.mini.core.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class ByteArrayWriter extends AbstractByteArrayWriter {

  /** . */
  private RandomAccessFile randomAccessFile;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ByteArrayWriter() {
    //
  }

  @Override
  public final void setRandomAccessFile(final RandomAccessFile randomAccessFile) {
    this.randomAccessFile = randomAccessFile;
  }

  @Override
  public final void writeToDestination(final byte[] bytes, final int offset, final int length) {
    try {
      this.randomAccessFile.write(bytes, offset, length);
    } catch (final IOException e) {
      throw new LogRuntimeException("", e);
    }
  }
}
