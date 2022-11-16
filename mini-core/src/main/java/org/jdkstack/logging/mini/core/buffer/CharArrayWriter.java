package org.jdkstack.logging.mini.core.buffer;

import java.io.BufferedWriter;
import java.io.IOException;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class CharArrayWriter extends AbstractCharArrayWriter {

  /** . */
  private BufferedWriter bufferedWriter;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public CharArrayWriter() {
    //
  }

  @Override
  public final void setBufferedWriter(final BufferedWriter bufferedWriter) {
    this.bufferedWriter = bufferedWriter;
  }

  @Override
  public final void readToDestination(final char[] bytes, final int offset, final int length) {
    try {
      this.bufferedWriter.write(bytes, offset, length);
      this.bufferedWriter.flush();
    } catch (final IOException e) {
      throw new LogRuntimeException("", e);
    }
  }
}
