package org.jdkstack.logging.mini.extension.buffer;

import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import jdk.internal.ref.Cleaner;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.buffer.Internal;
import sun.nio.ch.DirectBuffer;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class MmapByteArrayWriter extends ByteArrayWriter {

  /** MMAP文件固定1GB大小. */
  public static final long DEFAULT_REGION_LENGTH = 1L << 30;
  /** . */
  private MappedByteBuffer mappedBuffer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerOption h.
   * @author admin
   */
  public MmapByteArrayWriter(final HandlerOption handlerOption) {
    super(handlerOption);
  }

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
  public final void writeToDestination(final byte[] bytes, final int offset, final int length) {
    try {
      if (null == this.mappedBuffer) {
        this.remap();
      }
      // 切换日志文件规则只有一种,按size切换.
      // 数据的长度.
      int len = length;
      // 偏移量.
      int off = offset;
      // 剩余空间.
      int chunk = this.mappedBuffer.remaining();
      // 数据长度大于剩余空间,分段写.
      while (len > chunk) {
        // 一旦文件达到了上限(不能完整存储一条日志,只能存储半条)，重新打开一个文件.
        this.remap();
        // 写一次数据.
        this.mappedBuffer.put(bytes, off, chunk);
        // 偏移量增加写入的数据大小.
        off += chunk;
        // 数据长度减去写入的数据大小.
        len -= chunk;
        // 重新获取一次剩余空间.
        chunk = this.mappedBuffer.remaining();
      }
      // 数据长度小于等于剩余空间,直接写.
      this.mappedBuffer.put(bytes, off, len);
    } catch (final Exception e) {
      Internal.log(e);
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void remap() throws Exception {
    if (null != this.mappedBuffer) {
      // 强制刷新.
      this.mappedBuffer.force();
      // 断开文件句柄.
      final Cleaner cleaner = ((DirectBuffer) this.mappedBuffer).cleaner();
      if (null != cleaner) {
        cleaner.clean();
      }
    }
    // 调用父方法,先重新创建文件流.
    super.remap();
    // 重新映射文件.
    this.mappedBuffer = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, DEFAULT_REGION_LENGTH);
    // 设置写顺序.
    this.mappedBuffer.order(ByteOrder.nativeOrder());
  }
}
