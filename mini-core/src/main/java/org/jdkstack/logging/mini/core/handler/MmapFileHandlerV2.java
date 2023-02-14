package org.jdkstack.logging.mini.core.handler;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.MmapByteArrayWriter;
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
  private final ByteWriter destination = new MmapByteArrayWriter();

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
   * <p>.
   *
   * @author admin
   */
  @Override
  public final void process(final Record logRecord) {
    this.lock.lock();
    try {
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
