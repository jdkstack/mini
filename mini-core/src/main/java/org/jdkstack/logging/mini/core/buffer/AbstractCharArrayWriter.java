package org.jdkstack.logging.mini.core.buffer;

import java.nio.CharBuffer;
import org.jdkstack.logging.mini.api.buffer.CharWriter;

/**
 * .
 *
 * <p>将char[]字符数组写入文件.
 *
 * @author admin
 */
public abstract class AbstractCharArrayWriter implements CharWriter {

  /**
   * 256KB.
   */
  protected final CharBuffer charBuffer = CharBuffer.allocate(Constants.N256 << Constants.N10);

  @Override
  public final CharBuffer getCharBuffer() {
    return this.charBuffer;
  }

  @Override
  public final void flush(final CharBuffer charBuf) throws Exception {
    charBuf.flip();
    if (0 < charBuf.remaining()) {
      this.readToDestination(charBuf.array(), charBuf.arrayOffset() + charBuf.position(), charBuf.remaining());
    }
    charBuf.clear();
  }

  @Override
  public final void flush() throws Exception {
    this.flush(this.charBuffer);
  }
}
