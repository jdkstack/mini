package org.jdkstack.logging.mini.core.buffer;

import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.buffer.CharWriter;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractCharArrayWriter implements CharWriter {

  /** 256KB. */
  protected final CharBuffer charBuffer = CharBuffer.allocate(Constants.N256 << Constants.N10);

  @Override
  public final CharBuffer getCharBuffer() {
    return this.charBuffer;
  }


  @Override
  public final void flush(final CharBuffer charBuf) {
    try {
      charBuf.flip();
      if (0 < charBuf.remaining()) {
        this.readToDestination(charBuf.array(), charBuf.arrayOffset() + charBuf.position(), charBuf.remaining());
      }
    } finally {
      charBuf.clear();
    }
  }

  @Override
  public final void flush() {
    this.flush(this.charBuffer);
  }
}
