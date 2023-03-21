package org.jdkstack.logging.mini.core.buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.option.HandlerOption;
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
  /** . */
  protected final HandlerOption handlerOption;

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
  public void writeToDestination(final byte[] bytes, final int offset, final int length) {
    try {
      //切换日志文件规则.
      final String type = this.handlerOption.getType();
      // 1.按line切换.
      if (Constants.LINES.equals(type)) {
        final int lines = this.lines.incrementAndGet();
        //100W行切换一次.
        if (Constants.LC < lines) {
          this.remap();
          this.lines.set(1);
        }
        //2.按size切换.
      } else {
        final int sizes = this.sizes.addAndGet(length);
        // 100MB切换一次.
        if (Constants.SC < sizes) {
          this.remap();
          this.sizes.set(length);
        }
      }
      // 3.按照日期时间规则.
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
      // 重新计算文件名(创建临时对象?应该放到公共的地方.).
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
