package org.jdkstack.logging.mini.core.buffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class ByteArrayWriter extends AbstractByteArrayWriter {

  /** 按照文件大小切割. */
  protected final AtomicInteger sizes = new AtomicInteger(0);
  /** 按照文件条数切割. */
  protected final AtomicInteger lines = new AtomicInteger(0);
  /** . */
  protected final String key;
  /** . */
  protected RandomAccessFile randomAccessFile;
  /** . */
  protected FileChannel channel;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public ByteArrayWriter(final String key) {
    this.key = key;
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
  public void writeToDestination(final byte[] bytes, final int offset, final int length)
      throws Exception {
    if (null == this.randomAccessFile) {
      this.remap();
    }
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    // 切换日志文件规则.
    final String type = info1.getValue(key, "type");
    // 1.按line切换.
    if (Constants.LINES.equals(type)) {
      final int line = this.lines.incrementAndGet();
      // 100W行切换一次.
      if (Constants.LC < line) {
        this.remap();
        this.lines.set(1);
      }
      // 2.按size切换.
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
    if (null != this.randomAccessFile) {
      // 刷数据.
      this.flush();
      // 关闭channel.
      this.channel.close();
      // 关闭流.
      this.randomAccessFile.close();
    }
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    // 重新计算文件名(创建临时对象?应该放到公共的地方.).
    final File dir =
        new File(info1.getValue(key, "directory") + File.separator + info1.getValue(key, "prefix"));
    // 不存在,创建目录和子目录.
    if (!dir.exists()) {
      dir.mkdirs();
    }
    // 重新打开流.
    this.randomAccessFile =
        new RandomAccessFile(new File(dir, System.currentTimeMillis() + ".log"), "rw");
    // 重新打开流channel.
    this.channel = this.randomAccessFile.getChannel();
  }
}
