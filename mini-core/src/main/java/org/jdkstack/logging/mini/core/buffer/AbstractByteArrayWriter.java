package org.jdkstack.logging.mini.core.buffer;

import java.nio.ByteBuffer;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.lz4.Encoder;
import org.jdkstack.logging.mini.core.lz4.Base64;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.tool.ThreadLocalTool;

/**
 * .
 *
 * <p>将byte[]字节数组写入文件.
 *
 * @author admin
 */
public abstract class AbstractByteArrayWriter implements ByteWriter {

  /**
   * 256KB.
   */
  protected final ByteBuffer byteBuffer = ByteBuffer.allocate(Constants.N256 << Constants.N10);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void flush() throws Exception {
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
  public final void flush(final ByteBuffer buf) throws Exception {
    buf.flip();
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    final ByteBuffer out = logConsumeThread.getOut();
    final ByteBuffer out2 = logConsumeThread.getOut2();
    Encoder encoderLz4 = logConsumeThread.getEncoderLz4();
    encoderLz4.encode(buf, out);
    out.flip();
    Base64.encode(out, out2);
    try {
      // 如果有数据.
      if (0 < buf.remaining()) {
        // 开始写数据.
        this.writeToDestination(out2.array(), out2.arrayOffset() + out2.position(), out2.remaining());
      }
    } finally {
      out.clear();
      out2.clear();
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
