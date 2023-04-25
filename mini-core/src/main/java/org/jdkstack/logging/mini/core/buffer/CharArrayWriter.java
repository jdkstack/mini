package org.jdkstack.logging.mini.core.buffer;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.option.HandlerOption;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class CharArrayWriter extends AbstractCharArrayWriter {

  /** 按照文件大小切割. */
  protected final AtomicInteger sizes = new AtomicInteger(0);
  /** 按照文件条数切割. */
  protected final AtomicInteger lines = new AtomicInteger(0);
  /** . */
  protected final HandlerOption handlerOption;
  /** . */
  private BufferedWriter bufferedWriter;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerOption han .
   * @author admin
   */
  public CharArrayWriter(final HandlerOption handlerOption) {
    this.handlerOption = handlerOption;
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
  public final void readToDestination(final char[] bytes, final int offset, final int length) {
    try {
      if (null == this.bufferedWriter) {
        this.remap();
      }
      // 切换日志文件规则.
      final String type = this.handlerOption.getType();
      // 按line切换.
      if (Constants.LINES.equals(type)) {
        final int line = this.lines.incrementAndGet();
        // 100W行切换一次.
        if (Constants.LC < line) {
          this.remap();
          this.lines.set(1);
        }
        // 按size切换.
      } else {
        final int size = this.sizes.addAndGet(length);
        // 100MB切换一次.
        if (Constants.SC < size) {
          this.remap();
          this.sizes.set(length);
        }
      }
      this.bufferedWriter.write(bytes, offset, length);
      this.bufferedWriter.flush();
    } catch (final Exception e) {
      //
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  protected final void remap() throws Exception {
    // 关闭流.
    if (this.bufferedWriter != null) {
      // 刷数据.
      this.flush();
      this.bufferedWriter.close();
    }
    // 重新计算文件名(创建临时对象?).
    final File dir =
        new File(
            this.handlerOption.getDirectory() + File.separator + this.handlerOption.getPrefix());
    // 不存在,创建目录和子目录.
    if (!dir.exists()) {
      dir.mkdirs();
    }
    // 重新打开流.
    this.bufferedWriter =
        Files.newBufferedWriter(
            // 文件路径.
            new File(dir, System.currentTimeMillis() + ".log").toPath(),
            // 文件编码.
            StandardCharsets.UTF_8,
            // 创建文件.
            StandardOpenOption.CREATE,
            // 写文件.
            StandardOpenOption.WRITE,
            // 追加文件.
            StandardOpenOption.APPEND);
    // 尝试写入一个空字符串.
    this.bufferedWriter.write("");
  }
}
