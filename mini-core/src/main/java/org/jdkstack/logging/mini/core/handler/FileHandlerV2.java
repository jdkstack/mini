package org.jdkstack.logging.mini.core.handler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.datetime.TimeZone;
import org.jdkstack.logging.mini.core.formatter.LogFormatterV2;

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

  // 配置。
  private final RecorderConfig recorderConfig = this.context.getRecorderConfig(this.key);
  // -------------需要优化↓-------------------
  // 目录。
  private final String dirPath =
      recorderConfig.getDirectory() + File.separator + recorderConfig.getPrefix();
  private final File dir = new File(dirPath);
  // 文件。
  private final File source = new File(dir, recorderConfig.getFileName());

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
      // 创建目录。
      if (!dir.exists()) {
        dir.mkdirs();
      }
      // 重命名。
      this.renameFile();
      // 创建文件。
      this.remap();
    }
    // 切换日志文件规则.
    final String type = this.context.getRecorderConfig(this.key).getType();
    // 1.按line切换.
    if (org.jdkstack.logging.mini.core.buffer.Constants.LINES.equals(type)) {
      final int line = this.lines.incrementAndGet();
      // 100W行切换一次.
      if (org.jdkstack.logging.mini.core.buffer.Constants.LC < line) {
        // 每次切换文件时，都会创建20个对象，这个问题暂时无法解决(对无GC影响很小，但是需要解决才能100%达到无GC要求)。
        this.remap();
        this.lines.set(1);
      }
      // 2.按size切换.
    } else {
      final int size = this.sizes.addAndGet(length);
      // 100MB切换一次.
      if (org.jdkstack.logging.mini.core.buffer.Constants.SC < size) {
        // 每次切换文件时，都会创建20个对象，这个问题暂时无法解决(对无GC影响很小，但是需要解决才能100%达到无GC要求)。
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
      // 重命名。
      this.renameFile();
    }
    // 重新打开流.
    this.randomAccessFile = new RandomAccessFile(this.source, "rw");
    // 重新打开流channel.
    this.channel = this.randomAccessFile.getChannel();
    this.destination.setDestination(this.randomAccessFile);
  }

  private void renameFile() throws IOException {
    // 文件。
    final File target = new File(this.dir, System.currentTimeMillis() + ".log");
    if (this.source.exists()) {
      try {
        Files.move(
            Paths.get(this.source.getAbsolutePath()),
            Paths.get(target.getAbsolutePath()),
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING);
      } catch (final AtomicMoveNotSupportedException ex) {
        Files.move(
            Paths.get(this.source.getAbsolutePath()),
            Paths.get(target.getAbsolutePath()),
            StandardCopyOption.REPLACE_EXISTING);
      }
    }
  }

  /**
   * 从环形队列中获取元素对象.
   *
   * <p>将元素对象中的数据进行格式化,并写入缓存中,最后写入文件中.
   *
   * @param lr lr.
   * @author admin
   */
  @Override
  public void consume(final Record lr) throws Exception {
    RecorderConfig value = this.context.getRecorderConfig(this.key);
    // 格式化日志对象.
    final CharBuffer logMessage = (CharBuffer) this.context.formatter(value.getFormatter(), lr);
    // 清除缓存.
    this.charBuf.clear();
    // 将数据写入缓存.
    this.charBuf.put(logMessage.array(), logMessage.arrayOffset(), logMessage.remaining());
    // 开始读取的位置position=0,结束读取的位置limit=logMessage.length()
    this.charBuf.flip();
    // 切换规则(性能有些影响，需要优化。).
    this.rules(lr, this.charBuf.remaining());
    // 开始编码(1kb字符串,执行100W次花费2s).
    this.textEncoder.encode(this.charBuf, this.destination);
    // 单条刷新到磁盘(1kb字符串,执行100W次花费6s)，速度最慢，但是数据丢失机率最小，批量速度最好，但是数据丢失机率最大，并且日志被缓存，延迟写入文件.
    this.flush();
  }

  @Override
  public void produce(
      final String logLevel,
      final String dateTime,
      final String message,
      final String name,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9,
      final Throwable thrown,
      final Record lr) {
    // 设置日志级别.
    lr.setLevel(logLevel);
    // 设置日志日期时间.
    final StringBuilder event = lr.getEvent();
    // 如果参数为空,使用系统当前的时间戳,the current time(UTC 8) in milliseconds.
    if (null == dateTime) {
      // 系统当前的时间戳.
      final long current = System.currentTimeMillis();
      // 使用固定时区+8:00(8小时x3600秒).
      DateTimeEncoder.encoder(event, current, TimeZone.EAST8);
    } else {
      // 不处理参数传递过来的日期时间.
      event.append(dateTime);
    }
    // 设置9个参数.
    lr.setParams(arg1, 0);
    lr.setParams(arg2, 1);
    lr.setParams(arg3, 2);
    lr.setParams(arg4, 3);
    lr.setParams(arg5, 4);
    lr.setParams(arg6, 5);
    lr.setParams(arg7, 6);
    lr.setParams(arg8, 7);
    lr.setParams(arg9, 8);
    // 用9个参数替换掉message中的占位符{}.
    LogFormatterV2.format(lr, message);
    // location中的className,method和lineNumber,暂时不处理.
    lr.setName(name);
    // 异常信息.
    lr.setThrown(thrown);
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
