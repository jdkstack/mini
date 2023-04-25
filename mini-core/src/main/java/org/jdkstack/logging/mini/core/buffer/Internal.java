package org.jdkstack.logging.mini.core.buffer;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.codec.Constants;
import org.jdkstack.logging.mini.core.option.InternalOption;

/**
 * .
 *
 * <p>。
 *
 * @author admin
 */
public final class Internal {

  /** 临时数组. */
  private static final CharBuffer CHARBUF = CharBuffer.allocate(Constants.SOURCE);
  /** 字符编码器. */
  private static final Encoder<CharBuffer> TEXTENCODER =
      new CharArrayEncoderV2(Charset.defaultCharset());
  /** 目的地写入器. */
  private static final ByteWriter DESTINATION = new ByteArrayWriter(new InternalOption());

  private Internal() {
    //
  }

  /**
   * .
   *
   * <p>。
   *
   * @param e e.
   * @author admin
   */
  public static void log(final Exception e) {
    // 异常消息.
    final String message = e.getMessage();
    // 清除缓存.
    CHARBUF.clear();
    // 写入临时数组.
    message.getChars(0, message.length(), CHARBUF.array(), CHARBUF.arrayOffset());
    // 开始读取的位置.
    CHARBUF.position(0);
    // 结束读取的位置.
    CHARBUF.limit(message.length());
    // 开始编码.
    TEXTENCODER.encode(CHARBUF, DESTINATION);
    // 单条刷新到磁盘.
    DESTINATION.flush();
  }
}
