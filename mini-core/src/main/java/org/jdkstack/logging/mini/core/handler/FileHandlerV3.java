package org.jdkstack.logging.mini.core.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.CharWriter;
import org.jdkstack.logging.mini.api.codec.Decoder;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.buffer.CharArrayWriter;
import org.jdkstack.logging.mini.core.codec.ByteArrayDecoderV2;

/**
 * 写文件.
 *
 * <p>利用BufferedWriter方式写文件.
 *
 * @author admin
 */
public class FileHandlerV3 extends AbstractHandler {
  /** 按照文件大小切割. */
  private final AtomicInteger sizes = new AtomicInteger(0);
  /** 按照文件条数切割. */
  private final AtomicInteger lines = new AtomicInteger(0);
  /** 临时数组. */
  private final ByteBuffer byteBuf = ByteBuffer.allocate(Constants.SOURCE);
  /** 字符编码器. */
  private final Decoder<ByteBuffer> textEncoder = new ByteArrayDecoderV2(Charset.defaultCharset());
  /** 目的地写入器. */
  private final CharWriter destination = new CharArrayWriter();
  /** . */
  private BufferedWriter bufferedWriter;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public FileHandlerV3(final String key) {
    super(key);
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
    if (null == this.bufferedWriter) {
      this.remap();
    }
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    // 切换日志文件规则.
    final String type = info1.getValue(key, "type");
    // 按line切换.
    if (org.jdkstack.logging.mini.core.buffer.Constants.LINES.equals(type)) {
      final int line = this.lines.incrementAndGet();
      // 100W行切换一次.
      if (org.jdkstack.logging.mini.core.buffer.Constants.LC < line) {
        this.remap();
        this.lines.set(1);
      }
      // 按size切换.
    } else {
      final int size = this.sizes.addAndGet(length);
      // 100MB切换一次.
      if (org.jdkstack.logging.mini.core.buffer.Constants.SC < size) {
        this.remap();
        this.sizes.set(length);
      }
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public void remap() throws Exception {
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
    this.destination.setDestination(this.bufferedWriter);
  }

  /**
   * 从环形队列中获取元素对象.
   *
   * <p>将元素对象中的数据进行格式化,并写入缓存中,最后写入文件中.
   *
   * @param lr lr.
   * @author admin
   */
  public void consume(final Record lr)  throws Exception{
      if (this.filter(lr)) {
        // 格式化日志对象.
        final ByteBuffer logMessage = (ByteBuffer) this.format(lr);
        // 清除缓存.
        this.byteBuf.clear();
        // 将数据写入缓存.
        this.byteBuf.put(logMessage.array(), logMessage.arrayOffset(), logMessage.remaining());
        // 开始读取的位置,结束读取的位置.
        this.byteBuf.flip();
        // 切换规则.
        this.rules(lr, this.byteBuf.remaining());
        // 开始编码.
        this.textEncoder.decode(this.byteBuf, this.destination);
        // 单条刷新到磁盘.
        this.flush();
      }
  }

  /**
   * 刷新一次IO.
   *
   * <p>.
   *
   * @author admin
   */
  public void flush() throws Exception {
    this.destination.flush();
  }
}
