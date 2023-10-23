package org.jdkstack.logging.mini.core.handler;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.ringbuffer.FileRingBuffer;
import org.jdkstack.logging.mini.core.ringbuffer.RandomAccessFileRingBuffer;

/**
 * 写文件.
 *
 * <p>利用RandomAccessFile方式写文件.
 *
 * @author admin
 */
public class FileHandlerV2 extends AbstractHandler {
  /** 按照文件大小切割. */
  private final AtomicInteger sizes = new AtomicInteger(0);

  /** 按照文件条数切割. */
  private final AtomicInteger lines = new AtomicInteger(0);

  /** 目的地写入器. */
  private final ByteWriter destination = new ByteArrayWriter();

  /** 配置. */
  private final RecorderConfig rc = this.context.getRecorderConfig(this.key);

  /** 目录. */
  private final File dir = new File(this.rc.getDirectory() + File.separator + this.rc.getPrefix());

  /** . */
  private final RingBuffer<File> fileBuffer =
      new FileRingBuffer(this.dir, this.rc.getFileName(), this.rc.getFileNameExt(), 16);

  /** . */
  private final RingBuffer<RandomAccessFile> randomAccessFileBuffer =
      new RandomAccessFileRingBuffer(this.fileBuffer, 16);

  /** . */
  protected RandomAccessFile randomAccessFile;

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
   * @param lr lr.
   * @param length length.
   * @author admin
   */
  @Override
  public void rules(final Record lr, final int length) throws Exception {
    // 首次初始化。
    if (null == this.randomAccessFile) {
      // 创建文件。
      this.remap();
    }
    // 切换日志文件规则.
    final String type = this.context.getRecorderConfig(this.key).getType();
    switch (type) {
      case Constants.LINES:
        // 1.按line切换.
        final int line = this.lines.incrementAndGet();
        // 100W行切换一次.
        if (Constants.LC < line) {
          // 每次切换文件时，都会创建20个对象，这个问题暂时无法解决(对无GC影响很小，但是需要解决才能100%达到无GC要求)。
          this.remap();
          this.lines.set(1);
        }
        break;
      case Constants.SIZES:
        // 2.按size切换.
        final int size = this.sizes.addAndGet(length);
        // 100MB切换一次.
        if (Constants.SC < size) {
          // 每次切换文件时，都会创建20个对象，这个问题暂时无法解决(对无GC影响很小，但是需要解决才能100%达到无GC要求)。
          this.remap();
          this.sizes.set(length);
          this.lines.set(1);
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
    // 关闭流.
    if (null != this.randomAccessFile) {
      // 刷数据.
      this.flush();
    }
    // 从缓存中获取一个流.
    this.randomAccessFile = this.randomAccessFileBuffer.poll();
    //
    this.setDestination(this.destination);
    this.destination.setDestination(this.randomAccessFile);
  }
}
