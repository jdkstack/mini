package org.jdkstack.logging.mini.core.buffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.core.Internal;

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
  /** . */
  protected FileChannel channel;
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
   * @param handlerOption h.
   * @author admin
   */
  public ByteArrayWriter(final HandlerOption handlerOption) {
    this.handlerOption = handlerOption;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param bytes  b.
   * @param offset o.
   * @param length l.
   * @author admin
   */
  @Override
  public void writeToDestination(final byte[] bytes, final int offset, final int length) {
    try {
      if (null == this.randomAccessFile) {
        this.remap();
      }
      //切换日志文件规则.
      final String type = this.handlerOption.getType();
      // 1.按line切换.
      if (Constants.LINES.equals(type)) {
        final int line = this.lines.incrementAndGet();
        //100W行切换一次.
        if (Constants.LC < line) {
          this.remap();
          this.lines.set(1);
        }
        //2.按size切换.
      } else {
        final int size = this.sizes.addAndGet(length);
        // 100MB切换一次.
        if (Constants.SC < size) {
          this.remap();
          this.sizes.set(length);
        }
      }
      // 3.按照日期时间规则.
      this.randomAccessFile.write(bytes, offset, length);
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
  protected void remap() throws Exception {
    // 关闭流.
    if (this.randomAccessFile != null) {
      // 刷数据.
      this.flush();
      // 关闭channel.
      this.channel.close();
      // 关闭流.
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
    // 重新打开流channel.
    this.channel = this.randomAccessFile.getChannel();
  }
}
