package org.jdkstack.logging.mini.extension.handler;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.buffer.Internal;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.codec.Constants;
import org.jdkstack.logging.mini.core.handler.AbstractHandler;
import org.jdkstack.logging.mini.extension.buffer.MmapByteArrayWriter;

/**
 * 日志写入文件.
 *
 * <p>日志写入文件.
 *
 * @author admin
 */
public class MmapFileHandlerV2 extends AbstractHandler {

  /** 临时数组. */
  private final CharBuffer charBuf = CharBuffer.allocate(Constants.SOURCE);
  /** 字符编码器. */
  private final Encoder<CharBuffer> textEncoder = new CharArrayEncoderV2(Charset.defaultCharset());
  /** 目的地写入器. */
  private final ByteWriter destination;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerOption handlerOption.
   * @author admin
   */
  public MmapFileHandlerV2(final HandlerOption handlerOption) {
    super(handlerOption);
    this.destination = new MmapByteArrayWriter(handlerOption);
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
      // 清除缓存.
      this.charBuf.clear();
      // 写入临时数组.
      logMessage.getChars(0, logMessage.length(), this.charBuf.array(), this.charBuf.arrayOffset());
      // 结束读取的位置.
      this.charBuf.limit(logMessage.length());
      // 开始读取的位置.
      this.charBuf.position(0);
      // 开始编码.
      this.textEncoder.encode(this.charBuf, this.destination);
      // 单条刷新到磁盘.
      this.destination.flush();
    } catch (final Exception e) {
      Internal.log(e);
    } finally {
      this.lock.unlock();
    }
  }
}
