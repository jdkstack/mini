package org.jdkstack.logging.mini.core.handler;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.datetime.TimeZone;
import org.jdkstack.logging.mini.core.filter.RecorderFilter;
import org.jdkstack.logging.mini.core.formatter.LogFormatterV2;
import org.jdkstack.logging.mini.core.formatter.LogJsonFormatter;
import org.jdkstack.logging.mini.core.record.RecordEventFactory;
import org.jdkstack.logging.mini.core.ringbuffer.MpmcBlockingQueueV3;

/**
 * 写文件(内部使用).
 *
 * <p>利用RandomAccessFile方式写文件.
 *
 * @author admin
 */
public class SystemHandler {
  /** 内部使用固定的格式化. */
  private final Formatter logJsonFormatter = new LogJsonFormatter(null);

  /** 内部使用固定的过滤器. */
  private final Filter filter = new RecorderFilter(null, null);

  /** 有界数组阻塞队列. */
  private final MpmcBlockingQueueV3<Record> queue =
      new MpmcBlockingQueueV3<>(Constants.CAPACITY, new RecordEventFactory<>());

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
  public SystemHandler(final String key) {
    //
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
  public final void rules(final Record lr, final int length) throws Exception {
    if (null == this.randomAccessFile) {
      this.remap();
    }
    final int line = this.lines.incrementAndGet();
    // 100W行切换一次.
    if (org.jdkstack.logging.mini.core.buffer.Constants.LC < line) {
      this.remap();
      this.lines.set(1);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public final void remap() throws Exception {
    // 关闭流.
    if (null != this.randomAccessFile) {
      // 刷数据.
      this.flush();
      // 关闭channel.
      this.channel.close();
      // 关闭流.
      this.randomAccessFile.close();
    }
    // 重新计算文件名(创建临时对象?应该放到公共的地方.).
    final File dir = new File("logs" + File.separator + "system"); // 不存在,创建目录和子目录.
    if (!dir.exists()) {
      dir.mkdirs();
    }
    // 重新打开流.
    this.randomAccessFile = new RandomAccessFile(new File(dir, "system.log"), "rw");
    // 追加模式,跳过文件大小,然后继续写入数据.
    this.randomAccessFile.seek(this.randomAccessFile.length());
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
  public final void consume(final Record lr) throws Exception {
    if (this.filter.doFilter(lr)) {
      // 格式化日志对象.
      final CharBuffer logMessage = (CharBuffer) this.logJsonFormatter.format(lr);
      // 清除缓存.
      this.charBuf.clear();
      // 将数据写入缓存.
      this.charBuf.put(logMessage.array(), logMessage.arrayOffset(), logMessage.remaining());
      // 结束读取的位置.
      this.charBuf.limit(logMessage.length()); // 字符长度，不是字节长度。
      // 开始读取的位置.
      this.charBuf.position(0);
      // 切换规则.
      this.rules(lr, this.charBuf.remaining());
      // 开始编码(1kb字符串,执行100W次花费2s).
      this.textEncoder.encode(this.charBuf, this.destination);
      // 单条刷新到磁盘(1kb字符串,执行100W次花费6s)，速度最慢，但是数据丢失机率最小，批量速度最好，但是数据丢失机率最大，并且日志被缓存，延迟写入文件.
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
  public final void flush() throws Exception {
    this.destination.flush();
  }

  public final void produce(
          final String logLevel,
          final String dateTime,
          final String message,
          final String className,
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
    // location中的className(暂时不处理method和lineNumber).
    lr.setName(className);
    // 异常信息.
    lr.setThrown(thrown);
  }

  public final void process(
          final String logLevel,
          final String system,
          final String datetime,
          final String message,
          final Object arg1,
          final Object arg2,
          final Object arg3,
          final Object arg4,
          final Object arg5,
          final Object arg6,
          final Object arg7,
          final Object arg8,
          final Object arg9,
          final Throwable thrown) {
    // 单线程生产(向RingBuffer队列中生产).
    try {
      // 1.预生产(从循环队列tail取一个元素对象).
      final Record produceRecored = this.queue.tail();
      // 2.生产数据(为元素对象的每一个字段填充数据).
      this.produce(
          logLevel,
          datetime,
          message,
          system,
          arg1,
          arg2,
          arg3,
          arg4,
          arg5,
          arg6,
          arg7,
          arg8,
          arg9,
          thrown,
          produceRecored);
    } catch (final Exception ignored) {
      // Ignore this exception.
    } finally {
      // 3.生产数据完成的标记(数据可以从循环队列head消费).
      this.queue.start();
    }
    // 多线程消费(从RingBuffer队列中消费).
    try {
      // 1.预消费(从循环队列head取一个元素对象).
      final Record consumeRecord = this.queue.head();
      // 2.消费数据(从元素对象的每一个字段中获取数据).
      this.consume(consumeRecord);
      // 3.清空数据(为元素对象的每一个字段重新填充空数据).
      consumeRecord.clear();
    } catch (final Exception ignored) {
      // Ignore this exception.
    } finally {
      // 4.消费数据完成的标记(数据可以从循环队列tail生产).
      this.queue.end();
    }
  }
}
