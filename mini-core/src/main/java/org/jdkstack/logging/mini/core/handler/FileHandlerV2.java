package org.jdkstack.logging.mini.core.handler;

import java.nio.charset.Charset;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoder;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * 日志写入文件.
 *
 * <p>日志写入文件.
 *
 * @author admin
 */
public class FileHandlerV2 extends AbstractHandler {

  /** . */
  public final Encoder<StringBuilder> textEncoder = new CharArrayEncoder(Charset.defaultCharset());
  /** . */
  public final ByteWriter destination;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerOption handlerOption.
   * @author admin
   */
  public FileHandlerV2(
      final HandlerOption handlerOption) {
    super(handlerOption);
    this.destination = new ByteArrayWriter(handlerOption);
  }

  /**
   * 从队列获取日志记录,然后写入文件中.
   *
   * <p>.
   *
   * @author admin
   */
  @Override
  public void process(final Record logRecord) {
    this.lock.lock();
    try {
      // 格式化日志对象.
      final StringBuilder logMessage = this.format(logRecord);
      // 写入缓存.
      this.textEncoder.encode(logMessage, this.destination);
      // 单条刷新到磁盘.
      this.destination.flush();
    } catch (final Exception e) {
      throw new LogRuntimeException("bufferedWriter向文件写入数据时异常", e);
    } finally {
      this.lock.unlock();
    }
  }
}
