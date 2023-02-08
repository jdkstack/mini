package org.jdkstack.logging.mini.core.handler;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.MmapWriter;
import org.jdkstack.logging.mini.core.codec.StringBuilderEncoder;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * 日志写入文件.
 *
 * <p>日志写入文件.
 *
 * @author admin
 */
public class MmapFileHandlerV2 extends AbstractFileHandler {

  /** . */
  private RandomAccessFile randomAccessFile;
  /** . */
  private final Encoder<StringBuilder> textEncoder = new StringBuilderEncoder(Charset.defaultCharset());
  /** . */
  private final ByteWriter destination = new MmapWriter();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerOption handlerOption.
   * @author admin
   */
  public MmapFileHandlerV2(
      final HandlerOption handlerOption) {
    super(handlerOption);
    this.doConfig();
    this.doOpen();
  }

  /**
   * 从队列获取日志记录,然后写入文件中.
   *
   * <p>支持按照时间来滚动文件.
   * <pre>
   *       1.假设文件切换按照每10秒切换一次.
   *         int second = zdt.getSecond();
   *         则0秒的文件存储0-9秒的数据.
   *         则10秒的文件存储10-19秒的数据.
   *         则20秒的文件存储20-29秒的数据.
   *         则30秒的文件存储30-39秒的数据.
   *         则40秒的文件存储40-49秒的数据.
   *         则50秒的文件存储50-59秒的数据.
   *       2.用秒取模10,则结果是0-9.
   *         int remainder = second % 10;
   *       3.用秒减去(0-9),结果就是归并后的秒,即0-9归并到0,10-19规定到10以此类推.
   *         int merge = second - remainder;
   * </pre>
   *
   * @author admin
   */
  @Override
  public final void process(final Record logRecord) {
    this.lock.lock();
    try {
      // 获取归并后的区间.
      final long merge = this.getMerge(logRecord);
      // 如果不相等,则切换文件.
      if (this.policy.get() != merge) {
        // 设置文件名格式化的.
        this.check(logRecord);
        // 设置当前的秒.
        this.policy.set(merge);
        // 关闭当前流.
        this.doClose();
        // 重新配置流，会创建少量临时对象.
        this.doConfig();
        // 重新打开流，会创建少量临时对象.
        this.doOpen();
      }
      // 格式化日志对象.
      final StringBuilder logMessage = this.format(logRecord);
      // 写入缓存.
      this.textEncoder.encode(logMessage, this.destination);
      // 批量刷新到磁盘(会丢失数据,只有单条刷新到磁盘才不会丢失数据).
      final long mod = this.atomicLong.incrementAndGet() % this.batchSize;
      // 取模操作10亿次耗时5秒.
      if (0 == mod) {
        this.destination.flush();
      }
    } catch (final Exception e) {
      throw new LogRuntimeException("bufferedWriter向文件写入数据时异常", e);
    } finally {
      this.lock.unlock();
    }
  }

  /**
   * 创建文件流.
   *
   * <p>创建文件流.
   *
   * @author admin
   */
  @Override
  public final void doOpen() {
    try {
      this.randomAccessFile = new RandomAccessFile(this.path.toFile().getAbsoluteFile(), "rw");
      this.destination.setRandomAccessFile(this.randomAccessFile);
    } catch (final Exception e) {
      // 任何阶段发生了异常,主动关闭所有IO资源.
      this.doClose();
      throw new LogRuntimeException("打开bufferedWriter异常", e);
    }
  }

  /**
   * 关闭文件流.
   *
   * <p>关闭文件流.
   *
   * @author admin
   */
  @Override
  public final void doClose() {
    try {
      // 尝试关闭randomAccessFile流.
      if (null != this.randomAccessFile) {
        this.destination.flush();
        // 关闭文件流.
        this.randomAccessFile.close();
      }
    } catch (final Exception e) {
      throw new LogRuntimeException("关闭bufferedWriter流异常", e);
    }
  }

  @Override
  public final void doConfig() {
    // 创建目录.
    this.directory();
    // 拼接文件名.
    this.fileName();
    // 创建文件.
    this.file();
  }
}
