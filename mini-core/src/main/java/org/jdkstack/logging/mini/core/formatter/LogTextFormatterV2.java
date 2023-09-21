package org.jdkstack.logging.mini.core.formatter;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * 日志记录对象Record转成纯Text格式(空格分割).
 *
 * <p>按行输出纯文本格式的消息.
 *
 * @author admin
 */
public final class LogTextFormatterV2 implements Formatter {
  /** 临时数组. */
  private static final ByteBuffer CHARBUF =
      ByteBuffer.allocate(org.jdkstack.logging.mini.core.codec.Constants.SOURCEN8);

  private final Charset charset = Charset.defaultCharset();
  private final CharsetEncoder charsetEncoder =
      this.charset
          .newEncoder()
          .onMalformedInput(CodingErrorAction.REPLACE)
          .onUnmappableCharacter(CodingErrorAction.REPLACE);
  private final CharBuffer charBuffer = CharBuffer.allocate(2048);
  private final ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogTextFormatterV2() {
    //
  }

  private static void extracted(String lineSeparator) {
    if (lineSeparator == null) {
      lineSeparator = "";
    }
    for (int i = 0; i < lineSeparator.length(); i++) {
      CHARBUF.put((byte) lineSeparator.charAt(i));
    }
  }

  private static void extracted2(StringBuilder lineSeparator) {
    if (lineSeparator == null) {
      lineSeparator = new StringBuilder();
    }
    for (int i = 0; i < lineSeparator.length(); i++) {
      CHARBUF.put((byte) lineSeparator.charAt(i));
    }
  }

  public static void encodeText(
      final CharsetEncoder ce,
      final CharBuffer charBuf,
      final ByteBuffer byteBuf,
      final StringBuilder text)
      throws Exception {
    ce.reset();
    charBuf.clear();
    byteBuf.clear();
    for (int i = 0; i < text.length(); i++) {
      charBuf.put(text.charAt(i));
    }
    charBuf.flip();
    // 将字符数组编码成字节数组.
    ce.encode(charBuf, byteBuf, true);
    ce.flush(byteBuf);
    // 原来用!=比较.
    byteBuf.flip();
    if (0 != byteBuf.remaining()) {}
  }

  /**
   * 日志记录对象Record转成纯Text格式(空格分割).
   *
   * <p>日志记录对象Record转成纯Text格式(空格分割).
   *
   * @param logRecord 日志记录对象.
   * @return 志记录转换成文本格式.
   * @author admin
   */
  @Override
  public Buffer format(final Record logRecord) {
    // 文本格式的日志消息.
    CHARBUF.clear();
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    extracted(lineSeparator);
    CHARBUF.flip();
    return CHARBUF;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord .
   * @author admin
   */
  public void handle(final Record logRecord) {
    // 日志日期时间.
    extracted2(logRecord.getEvent());
    CHARBUF.put((byte) ' ');
    // 日志级别.
    extracted(logRecord.getLevelName());
    CHARBUF.put((byte) ' ');
    // 线程名.
    extracted(Thread.currentThread().getName());
    CHARBUF.put((byte) ' ');
    // 类.
    extracted(logRecord.getClassName());
    CHARBUF.put((byte) ' ');
    // 日志对象中的消息字段.
    final StringBuilder format = logRecord.getMessageText();
    final int position = CHARBUF.position();
    try {
      encodeText(this.charsetEncoder, this.charBuffer, this.byteBuffer, format);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    CHARBUF.put(this.byteBuffer);
    CHARBUF.position(position + this.byteBuffer.limit());
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      CHARBUF.put((byte) ' ');
      CHARBUF.put((byte) '[');
      extracted(thrown.getClass().getName());
      CHARBUF.put((byte) ':');
      extracted(thrown.getMessage());
      CHARBUF.put((byte) ',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      final int length = stackTraceElements.length;
      for (int i = 0; i < length; i++) {
        extracted(separator);
        final StackTraceElement stackTraceElement = stackTraceElements[i];
        extracted(stackTraceElement.getClassName());
        CHARBUF.put((byte) '.');
        extracted(stackTraceElement.getMethodName());
        CHARBUF.put((byte) '(');
        extracted(stackTraceElement.getFileName());
        CHARBUF.put((byte) ':');
        extracted("stackTraceElement.getLineNumber()");
        CHARBUF.put((byte) ')');
        separator = ",";
      }
      CHARBUF.put((byte) ']');
    }
  }
}
