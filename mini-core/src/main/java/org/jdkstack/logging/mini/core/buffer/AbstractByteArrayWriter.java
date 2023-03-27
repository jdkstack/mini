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

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void flush() {
    this.flush(this.byteBuffer);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param buf b.
   * @author admin
   */
  @Override
  public void flush(final ByteBuffer buf) {
    try {
      buf.flip();
      //如果有数据.
      if (0 < buf.remaining()) {
        //开始写数据.
        this.writeToDestination(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
      }
    } finally {
      // 清除缓存.
      buf.clear();
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return ByteBuffer .
   * @author admin
   */
  @Override
  public final ByteBuffer getByteBuffer() {
    return this.byteBuffer;
  }
}
