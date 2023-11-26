package org.jdkstack.logging.mini.core.handler;

import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;
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
  public void rules(final Record lr, final int length) throws Exception {
    final LogThread logThread = (LogThread) Thread.currentThread();
    RandomAccessFile randomAccessFile = logThread.getRandomAccessFile();
    AtomicInteger lines = logThread.getLines();
    AtomicInteger sizes = logThread.getSizes();
    // 首次初始化。
    if (null == randomAccessFile) {
      logThread.setRc(rc);
      // 创建文件。
      this.remap();
    }
    // 切换日志文件规则.
    final String type = rc.getType();
    switch (type) {
      case Constants.LINES:
        // 1.按line切换.
        final int line = lines.incrementAndGet();
        // 100W行切换一次.
        if (Constants.LC < line) {
          // 每次切换文件时，都会创建20个对象，这个问题暂时无法解决(对无GC影响很小，但是需要解决才能100%达到无GC要求)。
          this.remap();
          //lines.set(1);
          logThread.setLines(1);
        }
        break;
      case Constants.SIZES:
        // 2.按size切换.
        final int size = sizes.addAndGet(length);
        // 100MB切换一次.
        if (Constants.SC < size) {
          // 每次切换文件时，都会创建20个对象，这个问题暂时无法解决(对无GC影响很小，但是需要解决才能100%达到无GC要求)。
          this.remap();
          //sizes.set(length);
          //lines.set(1);
          logThread.setSizes(1);
          logThread.setLines(1);
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
