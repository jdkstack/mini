package org.jdkstack.logging.mini.core.handler;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.tool.ThreadLocalTool;

/**
 * 写文件.
 *
 * <p>多进程方式写文件 .
 * 文件锁分为2类： 排它锁：又叫独占锁。对文件加排它锁后，该进程可以对此文件进行读写，该进程独占此文件，其他进程不能读写此文件，直到该进程释放文件锁。 共享锁：某个进程对文件加共享锁，其他进程也可以访问此文件，但这些进程都只能读此文件，不能写。线程是安全的。只要还有一个进程持有共享锁，此文件就只能读，不能写。
 *
 * @author admin
 */
public class MultiProcessFileHandlerV2 extends FileHandlerV2 {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public MultiProcessFileHandlerV2(final LogRecorderContext context, final String key) {
    super(context, key);
  }

  /**
   * 切换文件的条件.
   *
   * <p>文件行数,文件大小,日期时间.
   *
   * @param lr     lr.
   * @param length length.
   * @author admin
   */
  @Override
  public void rules(final Record lr) throws Exception {
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    RandomAccessFile randomAccessFile = logConsumeThread.getRandomAccessFile();
    // 首次初始化。
    if (null == randomAccessFile) {
      logConsumeThread.setRc(rc);
      // 创建文件。
      this.remap();
    }
    // 获取 destination3。
    ByteWriter destination3 = logConsumeThread.getDestination3();
    // 切换日志文件规则.
    final String type = rc.getType();
    switch (type) {
      case Constants.LINES:
        long line1 = destination3.getLine();
        // 1.按line切换,10W行切换一次.
        if (Constants.LC <= line1) {
          this.remap();
        }
        break;
      case Constants.SIZES:
        long size1 = destination3.getSize();
        // 2.按size切换,100MB切换一次.
        if (Constants.SC <= size1) {
          this.remap();
        }
        break;
      default:
        break;
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public void remap() throws Exception {
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    RandomAccessFile randomAccessFile = logConsumeThread.getRandomAccessFile();
    // 关闭流.
    if (null != randomAccessFile) {
      // 刷数据.
      this.flush();
      // 强制刷新内容和元数据.
      FileChannel channel = logConsumeThread.getChannel();
      FileLock lock = logConsumeThread.getLock();
      channel.force(true);
      lock.release();
    }
    // 从缓存中获取一个流.
    randomAccessFile = logConsumeThread.getRandomAccessFileBuffer().poll();
    logConsumeThread.setRandomAccessFile(randomAccessFile);
    logConsumeThread.setChannel(randomAccessFile.getChannel());
    logConsumeThread.setLock(randomAccessFile.getChannel().lock(0, Long.MAX_VALUE, true));
    ByteWriter destination1 = logConsumeThread.getDestination();
    logConsumeThread.setDestination3(destination1);
    ByteWriter destination3 = logConsumeThread.getDestination3();
    destination3.setDestination(randomAccessFile);
  }
}
