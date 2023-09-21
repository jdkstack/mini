package org.jdkstack.logging.mini.core.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import org.jdkstack.logging.mini.api.buffer.CharWriter;
import org.jdkstack.logging.mini.api.codec.Decoder;

/**
 * .
 *
 * <p>将byte[] 字节数组解码成CharBuffer.
 *
 * @author admin
 */
public class ByteArrayDecoder implements Decoder<byte[]> {

  /** . */
  private final Charset charset;
  /** . */
  private final CharsetDecoder charsetDecoder;
  /** . */
  private final CharBuffer charBuffer;
  /** . */
  private final ByteBuffer byteBuffer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param charset .
   * @author admin
   */
  public ByteArrayDecoder(final Charset charset) {
    this(charset, Constants.DESTINATION, Constants.SOURCE);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param charset .
   * @param charBufferSize .
   * @param byteBufferSize .
   * @author admin
   */
  public ByteArrayDecoder(
      final Charset charset, final int charBufferSize, final int byteBufferSize) {
    this.charset = charset;
    this.charsetDecoder =
        this.charset
            .newDecoder()
            .onMalformedInput(CodingErrorAction.REPLACE)
            .onUnmappableCharacter(CodingErrorAction.REPLACE);
    this.charBuffer = CharBuffer.allocate(charBufferSize);
    this.byteBuffer = ByteBuffer.allocate(byteBufferSize);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param cd .
   * @param charBuf .
   * @param byteBuf .
   * @param text .
   * @param reader .
   * @author admin
   */
  public static void encodeText(
      final CharsetDecoder cd,
      final CharBuffer charBuf,
      final ByteBuffer byteBuf,
      final byte[] text,
      final CharWriter reader)
      throws Exception {
    cd.reset();
    byteBuf.clear();
    byteBuf.put(text, 0, text.length);
    // byteBuf.limit(text.length);
    // byteBuf.position(0);
    byteBuf.flip();
    cd.decode(byteBuf, charBuf, true);
    cd.flush(charBuf);
    charBuf.flip();
    if (0 != charBuf.remaining()) {
      writeTo(charBuf, reader);
    }
    charBuf.clear();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source .
   * @param destination .
   * @author admin
   */
  public static void writeTo(final CharBuffer source, final CharWriter destination)
      throws Exception {
    final CharBuffer destBuff = destination.getCharBuffer();
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
   * @author admin
   */
  @Override
  public final void decode(final byte[] source, final CharWriter reader) throws Exception {
    encodeText(this.charsetDecoder, this.charBuffer, this.byteBuffer, source, reader);
  }
}
