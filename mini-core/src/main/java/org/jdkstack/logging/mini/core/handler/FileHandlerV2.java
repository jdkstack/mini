package org.jdkstack.logging.mini.core.handler;

import java.io.RandomAccessFile;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.thread.LogThread;

/**
 * 写文件.
 *
 * <p>利用RandomAccessFile方式写文件.
 *
 * @author admin
 */
public class FileHandlerV2 extends AbstractHandler {

  /**
   * 配置.
   */
  protected final HandlerConfig rc = this.context.getHandlerConfig(this.key);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public FileHandlerV2(final LogRecorderContext context, final String key) {
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
    final LogThread logThread = (LogThread) Thread.currentThread();
    RandomAccessFile randomAccessFile = logThread.getRandomAccessFile();
    // 首次初始化。
    if (null == randomAccessFile) {
      logThread.setRc(rc);
      // 创建文件。
      this.remap();
    }
    // 获取 destination3。
    ByteWriter destination3 = logThread.getDestination3();
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
    final LogThread logThread = (LogThread) Thread.currentThread();
    RandomAccessFile randomAccessFile = logThread.getRandomAccessFile();
    // 关闭流.
    if (null != randomAccessFile) {
      // 刷数据.
      this.flush();
    }
    // 从缓存中获取一个流.
    randomAccessFile = logThread.getRandomAccessFileBuffer().poll();
    logThread.setRandomAccessFile(randomAccessFile);
    ByteWriter destination1 = logThread.getDestination();
    logThread.setDestination3(destination1);
    ByteWriter destination3 = logThread.getDestination3();
    destination3.setDestination(randomAccessFile);
  }
}
