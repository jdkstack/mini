package org.jdkstack.logging.mini.core.handler;

import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.tool.ThreadLocalTool;

/**
 * 写文件.
 *
 * <p>利用MappedByteBuffer方式写文件 .
 *
 * @author admin
 */
public class MmapFileHandlerV2 extends FileHandlerV2 {

  /**
   * MMAP文件固定1GB大小.
   */
  private static final long DEFAULT_REGION_LENGTH = 1L << 30;

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
   * @param lr     lr.
   * @param length length.
   * @author admin
   */
  @Override
  public final void rules(final Record lr) throws Exception {
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    MappedByteBuffer mappedBuffer = logConsumeThread.getMappedBuffer();
    if (null == mappedBuffer) {
      logConsumeThread.setRc(rc);
      this.remap();
    } else {
      // 切换日志文件规则只有一种,按size切换.
      // 剩余空间.
      final int chunk = mappedBuffer.remaining();
      // 获取 destination3。
      ByteWriter destination3 = logConsumeThread.getDestination3();
      long size1 = destination3.getSize();
      // 数据长度大于剩余空间,分段写.  
      if (size1 > chunk) {
        // 一旦文件达到了上限(不能完整存储一条日志,只能存储半条)，重新打开一个文件.
        this.remap();
      }
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
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    MappedByteBuffer mappedBuffer = logConsumeThread.getMappedBuffer();
    if (null != mappedBuffer) {
      // 刷数据.
      this.flush();
      // 强制刷新内容和元数据.
      mappedBuffer.force();
    }
    // 调用父方法,先重新创建文件流.
    super.remap();
    RandomAccessFile randomAccessFile = logConsumeThread.getRandomAccessFile();
    // 重新打开流channel.
    FileChannel channel = randomAccessFile.getChannel();
    // 重新映射文件.
    mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, DEFAULT_REGION_LENGTH);
    // 设置写顺序.
    mappedBuffer.order(ByteOrder.nativeOrder());
    logConsumeThread.setMappedBuffer(mappedBuffer);
    ByteWriter destination1 = logConsumeThread.getMmapByteArrayWriter();
    logConsumeThread.setDestination3(destination1);
    ByteWriter destination3 = logConsumeThread.getDestination3();
    destination3.setDestination(mappedBuffer);
  }
}
