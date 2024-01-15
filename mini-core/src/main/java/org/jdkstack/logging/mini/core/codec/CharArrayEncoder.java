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
 * <p>将char[] 字符数组编码成ByteBuffer.
 *
 * @author admin
 */
public class CharArrayEncoder implements CodecEncoder<char[]> {

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
  public CharArrayEncoder(final Charset charset) {
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
  public CharArrayEncoder(final Charset charset, final int charBufferSize, final int byteBufferSize) {
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
  public static void encodeText(final CharsetEncoder ce, final CharBuffer charBuf, final ByteBuffer byteBuf, final char[] text, final ByteWriter destination) throws Exception {
    ce.reset();
    charBuf.clear();
    charBuf.put(text, 0, text.length);
    // 结束位置.
    // charBuf.limit(text.length);
    // 开始位置.
    // charBuf.position(0);
    charBuf.flip();
    // 将字符数组编码成字节数组.
    ce.encode(charBuf, byteBuf, true);
    ce.flush(byteBuf);
    // 原来用!=比较.
    if (!byteBuf.equals(destination.getByteBuffer())) {
      byteBuf.flip();
      if (0 != byteBuf.remaining()) {
        writeTo(byteBuf, destination);
      }
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
    final ByteBuffer destBuff = destination.getByteBuffer();
    while (source.remaining() > destBuff.remaining()) {
      // final int originalLimit = source.limit();
      // source.limit(Math.min(source.limit(), source.position() + destBuff.remaining()));
      // destBuff.put(source);
      // source.limit(originalLimit);
      destination.flush(destBuff);
    }
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
  public final void encode(final char[] source, final ByteWriter destination) throws Exception {
    encodeText(this.charsetEncoder, this.charBuffer, this.byteBuffer, source, destination);
  }
}
