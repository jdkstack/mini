package org.jdkstack.logging.mini.extension.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.codec.Constants;
import org.jdkstack.logging.mini.core.handler.FileHandlerV2;
import org.jdkstack.logging.mini.extension.buffer.MmapByteArrayWriter;

/**
 * 写文件.
 *
 * <p>利用MappedByteBuffer方式写文件.
 *
 * @author admin
 */
public class MmapFileHandlerV2 extends FileHandlerV2 {
  /** MMAP文件固定1GB大小. */
  private static final long DEFAULT_REGION_LENGTH = 1L << 30;
  /** . */
  private MappedByteBuffer mappedBuffer;
  /** 临时数组. */
  private final CharBuffer charBuf = CharBuffer.allocate(Constants.SOURCE);
  /** 字符编码器. */
  private final Encoder<CharBuffer> textEncoder = new CharArrayEncoderV2(Charset.defaultCharset());
  /** 目的地写入器. */
  private final ByteWriter destination = new MmapByteArrayWriter();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  public MmapFileHandlerV2(final String key) {
    super(key);
  }

  /**
   * 切换文件的条件.
   *
   * <p>文件大小.
   *
   * @param lr lr.
   * @param length length.
   * @author admin
   */
  @Override
  public void rules(final Record lr, final int length) throws Exception {
    if (null == this.mappedBuffer) {
      this.remap();
    }
    // 切换日志文件规则只有一种,按size切换.
    // 剩余空间.
    final int chunk = this.mappedBuffer.remaining();
    // 数据长度大于剩余空间,分段写.
    if (length > chunk) {
      // 一旦文件达到了上限(不能完整存储一条日志,只能存储半条)，重新打开一个文件.
      this.remap();
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void remap() throws Exception {
    if (null != this.mappedBuffer) {
      // 强制刷新.
      this.mappedBuffer.force();
      // 断开文件句柄，使用反射调用释放方法.
      final Field field = this.mappedBuffer.getClass().getDeclaredField("cleaner");

      final Object cleaner = field.get(this.mappedBuffer);
      final Method cleanMethod = cleaner.getClass().getMethod("clean");
      cleanMethod.invoke(cleaner);
    }
    // 调用父方法,先重新创建文件流.
    super.remap();
    // 重新映射文件.
    this.mappedBuffer = this.channel.map(FileChannel.MapMode.READ_WRITE, 0, DEFAULT_REGION_LENGTH);
    // 设置写顺序.
    this.mappedBuffer.order(ByteOrder.nativeOrder());
    this.destination.setDestination(this.mappedBuffer);
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
  public void consume(final Record lr)  throws Exception{
      if (this.filter(lr)) {
        // 格式化日志对象.
        final CharBuffer logMessage = (CharBuffer) this.format(lr);
        // 清除缓存.
        this.charBuf.clear();
        // 写入临时数组.
        // 将数据写入缓存.
        this.charBuf.put(logMessage.array(), logMessage.arrayOffset(), logMessage.remaining());
        // 结束读取的位置.
        this.charBuf.limit(logMessage.length());
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
  @Override
  public void flush() throws Exception {
    this.destination.flush();
  }
}
