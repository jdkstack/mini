package org.jdkstack.logging.mini.core.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.CodecEncoder;

/**
 * .
 *
 * <p>将CharBuffer 字符buffer编码成ByteBuffer.
 *
 * @author admin
 */
public class CharArrayEncoderV2 implements CodecEncoder<CharBuffer> {

  /**
   * .
   */
  private final Charset charset;
  /**
   * .
   */
  private final CharsetEncoder charsetEncoder;
  /**
   * .
   */
  private final CharBuffer charBuffer;
  /**
   * .
   */
  private final ByteBuffer byteBuffer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param charset .
   * @author admin
   */
  public CharArrayEncoderV2(final Charset charset) {
    this(charset, Constants.SOURCE, Constants.DESTINATION);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param charset        .
   * @param charBufferSize .
   * @param byteBufferSize .
   * @author admin
   */
  public CharArrayEncoderV2(final Charset charset, final int charBufferSize, final int byteBufferSize) {
    this.charset = charset;
    this.charsetEncoder = this.charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
    this.charBuffer = CharBuffer.allocate(charBufferSize);
    this.byteBuffer = ByteBuffer.allocate(byteBufferSize);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param ce          .
   * @param charBuf     .
   * @param byteBuf     .
   * @param text        .
   * @param destination .
   * @author admin
   */
  public static void encodeText(final CharsetEncoder ce, final CharBuffer charBuf, final ByteBuffer byteBuf, final CharBuffer text, final ByteWriter destination) throws Exception {
    // 重置字符编码器.
    ce.reset();
    // 清除缓存.
    charBuf.clear();
    // 将数据写入缓存.
    charBuf.put(text.array(), text.arrayOffset(), text.remaining());
    // 结束读取位置.
    // charBuf.limit(text.remaining());
    // 开始读取位置.
    // charBuf.position(0);
    charBuf.flip();
    // 将字符数组编码成字节数组.
    ce.encode(charBuf, byteBuf, true);
    // 刷新一下缓存.
    ce.flush(byteBuf);
    // 原来用!=比较.
    if (!byteBuf.equals(destination.getByteBuffer())) {
      // 翻转limit和position,将限制设置为当前位置,然后将位置设置为零.
      byteBuf.flip();
      // 如果有可读取的数据.
      if (0 != byteBuf.remaining()) {
        // 开始将数据写入目的地.
        writeTo(byteBuf, destination);
      }
      // 清除缓存.
      byteBuf.clear();
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source      .
   * @param destination .
   * @author admin
   */
  public static void writeTo(final ByteBuffer source, final ByteWriter destination) throws Exception {
    // 得到字节数组数据.
    final ByteBuffer destBuff = destination.getByteBuffer();
    // 如果空间不足,分批写入.
    while (source.remaining() > destBuff.remaining()) {
      /* 可能导致，一条日志只有一部分写入文件，另一部分丢失。
      final int originalLimit = source.limit();
      source.limit(Math.min(source.limit(), source.position() + destBuff.remaining()));
      destBuff.put(source);
      source.limit(originalLimit);*/
      destination.flush(destBuff);
    }
    // 如果空间足够,直接写入.
    destBuff.put(source);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source      .
   * @param destination .
   * @author admin
   */
  @Override
  public final void encode(final CharBuffer source, final ByteWriter destination) throws Exception {
    encodeText(this.charsetEncoder, this.charBuffer, this.byteBuffer, source, destination);
  }
}
