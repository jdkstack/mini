package org.jdkstack.logging.mini.core.handler;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;

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
  /** 临时数组. */
  private final CharBuffer charBuf = CharBuffer.allocate(Constants.SOURCE);
  /** 字符编码器. */
  private final Encoder<CharBuffer> textEncoder = new CharArrayEncoderV2(Charset.defaultCharset());
  /** 目的地写入器. */
  private final ByteWriter destination = new ByteArrayWriter();
  /** . */
  protected FileChannel channel;
  /** . */
  private RandomAccessFile randomAccessFile;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public FileHandlerV2(final String key) {
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
    if (null == this.randomAccessFile) {
      this.remap();
    }
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    // 切换日志文件规则.
    final String type = info1.getValue(key, "type");
    // 1.按line切换.
    if (org.jdkstack.logging.mini.core.buffer.Constants.LINES.equals(type)) {
      final int line = this.lines.incrementAndGet();
      // 100W行切换一次.
      if (org.jdkstack.logging.mini.core.buffer.Constants.LC <= line) {
        this.remap();
        this.lines.set(1);
      }
      // 2.按size切换.
    } else {
      final int size = this.sizes.addAndGet(length);
      final int line = this.lines.incrementAndGet();
      // 100MB切换一次.
      if (org.jdkstack.logging.mini.core.buffer.Constants.SC <= size) {
        System.out.println(size);
        System.out.println(line);
        this.remap();
        this.sizes.set(length);
        this.lines.set(1);
      }
    }
    // 3.按照日期时间规则.
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
    this.destination.setDestination(this.randomAccessFile);
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
        final CharBuffer logMessage = (CharBuffer) this.format(lr);
        // 清除缓存.
        this.charBuf.clear();
        // 将数据写入缓存.
        this.charBuf.put(logMessage.array(), logMessage.arrayOffset(), logMessage.remaining());
        // 结束读取的位置.
        this.charBuf.limit(logMessage.length());//字符长度，不是字节长度。
        // 开始读取的位置.
        this.charBuf.position(0);
        // 切换规则.
        this.rules(lr, this.charBuf.remaining());
        // 开始编码.
        this.textEncoder.encode(this.charBuf, this.destination);
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
