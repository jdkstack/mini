package org.jdkstack.logging.mini.core.buffer;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
public class CharArrayWriter extends AbstractCharArrayWriter {

  /** 按照文件大小切割. */
  protected final AtomicInteger sizes = new AtomicInteger(0);
  /** 按照文件条数切割. */
  protected final AtomicInteger lines = new AtomicInteger(0);
  /** . */
  protected final String key;
  /** . */
  private BufferedWriter bufferedWriter;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key .
   * @author admin
   */
  public CharArrayWriter(final String key) {
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
  public final void readToDestination(final char[] bytes, final int offset, final int length)
      throws Exception {
    if (null == this.bufferedWriter) {
      this.remap();
    }
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    // 切换日志文件规则.
    final String type = info1.getValue(key, "type");
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
    if (null != this.bufferedWriter) {
      // 刷数据.
      this.flush();
      this.bufferedWriter.close();
    }
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    // 重新计算文件名(创建临时对象?).
    final File dir =
        new File(info1.getValue(key, "directory") + File.separator + info1.getValue(key, "prefix"));
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
