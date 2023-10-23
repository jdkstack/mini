package org.jdkstack.logging.mini.core.handler;

import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.MmapByteArrayWriter;

/**
 * 写文件.
 *
 * <p>利用MappedByteBuffer方式写文件 .
 *
 * @author admin
 */
public class MmapFileHandlerV2 extends FileHandlerV2 {
  /** MMAP文件固定1GB大小. */
  private static final long DEFAULT_REGION_LENGTH = 1L << 30;

  /** 目的地写入器. */
  private final ByteWriter destination = new MmapByteArrayWriter();

  /** . */
  private MappedByteBuffer mappedBuffer;

  /** . */
  private FileChannel channel;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public MmapFileHandlerV2(final LogRecorderContext context, final String key) {
    super(context, key);
  }

  /**
   * 切换文件的条件.
   *
   * <p>文件大小.
   *
   * @param lr lr.
   * @param length length.
   * @author admin
   */
  @Override
  public final void rules(final Record lr, final int length) throws Exception {
    if (null == this.mappedBuffer) {
      this.remap();
    }
    // 切换日志文件规则只有一种,按size切换.
    // 剩余空间.
    final int chunk = this.mappedBuffer.remaining();
    // 数据长度大于剩余空间,分段写.  todo: 不使用这个方法时，有问题，this.flush。
    if (length > chunk) {
      // 一旦文件达到了上限(不能完整存储一条日志,只能存储半条)，重新打开一个文件.
      this.remap();
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
    }
    // 调用父方法,先重新创建文件流.
    super.remap();
    // 重新打开流channel.
    this.channel = this.randomAccessFile.getChannel();
    // 重新映射文件.
    this.mappedBuffer = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, DEFAULT_REGION_LENGTH);
    // 设置写顺序.
    this.mappedBuffer.order(ByteOrder.nativeOrder());
    this.setDestination(this.destination);
    this.destination.setDestination(this.mappedBuffer);
  }
}
