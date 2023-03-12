package org.jdkstack.logging.mini.core.buffer;

import java.nio.ByteBuffer;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractByteArrayWriter implements ByteWriter {

  /** 256KB. */
  protected final ByteBuffer byteBuffer = ByteBuffer.allocate(Constants.N256 << Constants.N10);

  @Override
  public final void flush() {
    this.flush(this.byteBuffer);
  }

  @Override
  public void flush(final ByteBuffer buf) {
    try {
      buf.flip();
      if (0 < buf.remaining()) {
        this.writeToDestination(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
      }
    } finally {
      buf.clear();
    }
  }

  @Override
  public final ByteBuffer getByteBuffer() {
    return this.byteBuffer;
  }
}
