package org.jdkstack.logging.mini.core.buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class ByteArrayWriter extends AbstractByteArrayWriter {

  /** . */
  protected RandomAccessFile randomAccessFile;
  /** 按照文件大小切割. */
  protected final AtomicInteger sizes = new AtomicInteger(0);
  /** 按照文件条数切割. */
  protected final AtomicInteger lines = new AtomicInteger(0);

  protected final HandlerOption handlerOption;
  /** 日志文件切换开关. */
  protected final AtomicLong policy = new AtomicLong(-1L);

  private String format1;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ByteArrayWriter(final HandlerOption handlerOption) {
    this.handlerOption = handlerOption;
    this.remap();
  }

  @Override
  public void setRandomAccessFile(final RandomAccessFile randomAccessFile) {
    this.randomAccessFile = randomAccessFile;
  }

  /**
   * 切换日志文件规则.
   *
   * <p>切换日志文件规则.
   *
   * @param logRecord logRecord
   * @return int .
   * @author admin
   */
  protected final long getMerge(final Record logRecord) {
    final String type = this.handlerOption.getType();
    final long interval;
    switch (type) {
      case Constants.SECOND:
        interval = logRecord.getSecond();
        break;
      case Constants.MINUTE:
        interval = logRecord.getMinute();
        break;
      case Constants.HOUR:
        interval = logRecord.getHours();
        break;
      case Constants.DAY:
        interval = logRecord.getDay() - 1;
        break;
      case Constants.MONTH:
        interval = logRecord.getMonth() - 1;
        break;
      default:
        throw new LogRuntimeException("不支持.");
    }
    final long remainder = interval % Long.parseLong(this.handlerOption.getInterval());
    return interval - remainder;
  }

  protected final void check(final Record logRecord) {
    final String type = this.handlerOption.getType();
    final long year = logRecord.getYear();
    final long month = logRecord.getMonth();
    final long day = logRecord.getDay();
    final long hours = logRecord.getHours();
    final long minute = logRecord.getMinute();
    switch (type) {
      case Constants.SECOND:
        this.format1 = year + Constants.SEPARATOR + month + Constants.SEPARATOR + day
            + Constants.SEPARATOR + hours + Constants.SEPARATOR + minute;
        break;
      case Constants.MINUTE:
        this.format1 = year + Constants.SEPARATOR + month + Constants.SEPARATOR + day + Constants.SEPARATOR + hours;
        break;
      case Constants.HOUR:
        this.format1 = year + Constants.SEPARATOR + month + Constants.SEPARATOR + day;
        break;
      case Constants.DAY:
        this.format1 = year + Constants.SEPARATOR + month;
        break;
      case Constants.MONTH:
        this.format1 = year + Constants.SEPARATOR;
        break;
      default:
        throw new LogRuntimeException("不支持.");
    }
  }

  @Override
  public void writeToDestination(final byte[] bytes, final int offset, final int length) {
    try {
      // 按line切换.
      final int lines = this.lines.incrementAndGet();
      //100W行切换一次.
      if (lines > 1000000) {
        this.remap();
        this.lines.set(1);
      }
/*      //按size切换.
      final int sizes = this.sizes.addAndGet(length);
      // 100MB切换一次.
      if (sizes > 104857600) {
        this.remap();
        this.sizes.set(length);
      }*/
      this.randomAccessFile.write(bytes, offset, length);
    } catch (final IOException e) {
      throw new LogRuntimeException("", e);
    }
  }

  protected void remap() {
    try {
      // 关闭流.
      if (this.randomAccessFile != null) {
        // 刷数据.
        this.flush();
        this.randomAccessFile.close();
      }
      // 重新计算文件名(创建临时对象?).
      final File dir = new File(this.handlerOption.getDirectory() + File.separator + this.handlerOption.getPrefix());
      // 不存在,创建目录和子目录.
      if (!dir.exists()) {
        dir.mkdirs();
      }
      //重新打开流.
      this.randomAccessFile = new RandomAccessFile(new File(dir, System.currentTimeMillis() + ".log"), "rw");
    } catch (
        final Exception ex) {
      System.out.println("Unable to remap" + ex.getMessage());
    }
  }
}
