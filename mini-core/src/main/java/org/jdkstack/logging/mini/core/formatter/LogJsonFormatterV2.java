package org.jdkstack.logging.mini.core.formatter;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.codec.Constants;

/**
 * 日志记录对象Record转成Json格式.
 *
 * <p>按行输出纯json格式的消息.
 *
 * @author admin
 */
public final class LogJsonFormatterV2 implements Formatter {
  /** 临时数组. */
  private static final ByteBuffer CHARBUF = ByteBuffer.allocate(Constants.SOURCEN8);
  Charset charset = Charset.defaultCharset();
  CharsetEncoder charsetEncoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
  CharBuffer charBuffer = CharBuffer.allocate(2048);
  ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
  /** . */
  private String dateTimeFormat;
  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogJsonFormatterV2() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTimeFormat 日期格式.
   * @author admin
   */
  public LogJsonFormatterV2(final String dateTimeFormat) {
    this.dateTimeFormat = dateTimeFormat;
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
    for(int i=0;i<text.length();i++){
      charBuf.put(text.charAt(i));
    }
    charBuf.flip();
    // 将字符数组编码成字节数组.
    ce.encode(charBuf, byteBuf, true);
    ce.flush(byteBuf);
    // 原来用!=比较.
    byteBuf.flip();
    if (0 != byteBuf.remaining()) {

    }
  }

  private static void extracted(String lineSeparator) {
    if(lineSeparator==null){
      lineSeparator="";
    }
    for(int i = 0; i< lineSeparator.length(); i++){
        CHARBUF.put((byte) lineSeparator.charAt(i));
    }
  }

  private static void extracted2(StringBuilder lineSeparator) {
    if(lineSeparator==null){
      lineSeparator=new StringBuilder();
    }
    for(int i = 0; i< lineSeparator.length(); i++){
      CHARBUF.put((byte) lineSeparator.charAt(i));
    }
  }

  /**
   * 日志记录对象Record转成Json格式.
   *
   * <p>日志记录对象Record转成Json格式.
   *
   * @param logRecord 日志记录对象.
   * @return 日志记录转换成json格式.
   * @author admin
   */
  @Override
  public Buffer format(final Record logRecord) {
    // json格式的日志消息.
    CHARBUF.clear();
    // json字符串开始.
    CHARBUF.put((byte) '{');
    // 日志对象中的特殊字段.
    this.handle(logRecord);
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    // json字符串结束.
    CHARBUF.put((byte) '}');
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
    CHARBUF.put((byte)'"');
    extracted("dateTime");
    CHARBUF.put((byte)'"');
    CHARBUF.put((byte)':');
    CHARBUF.put((byte)'"');
    StringBuilder dateTime = logRecord.getEvent();
    extracted2(dateTime);
    CHARBUF.put((byte) '"');
    CHARBUF.put((byte)',');
    //CHARBUF.append("\"levelName\"");
    extracted("\"levelName\"");
    CHARBUF.put((byte)':');
    // 日志级别.
    CHARBUF.put((byte)'"');
    extracted(logRecord.getLevelName());
    CHARBUF.put((byte)'"');
    CHARBUF.put((byte)',');
    extracted("\"threadName\"");
    CHARBUF.put((byte)':');
    // 线程名.
    CHARBUF.put((byte)'"');
    extracted(Thread.currentThread().getName());
    CHARBUF.put((byte)'"');
    CHARBUF.put((byte)',');
    extracted("\"className\"");
    CHARBUF.put((byte)':');
    // 类.
    CHARBUF.put((byte)'"');
    extracted(logRecord.getClassName());
    CHARBUF.put((byte)'"');
    CHARBUF.put((byte)',');
    extracted("\"message\"");
    CHARBUF.put((byte)':');
    // 日志对象中的消息字段.
    CHARBUF.put((byte) '"');
    int position = CHARBUF.position();
    final StringBuilder format = logRecord.getMessageText();
    try {
      encodeText(charsetEncoder, charBuffer, byteBuffer, format);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    CHARBUF.put(byteBuffer);
    CHARBUF.position(position+byteBuffer.limit());
    CHARBUF.put((byte)'"');
    // 日志对象中的异常堆栈信息.
    final Throwable thrown = logRecord.getThrowable();
    if (null != thrown) {
      CHARBUF.put((byte)',');
      extracted("\"stacktrace\"");
      extracted("\"levelName\"");
      CHARBUF.put((byte)':');
      CHARBUF.put((byte)'[');
      CHARBUF.put((byte)'"');
      extracted(thrown.getClass().getName());
      CHARBUF.put((byte)':');
      extracted(thrown.getMessage());
      CHARBUF.put((byte)'"');
      CHARBUF.put((byte)',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      final int length = stackTraceElements.length;
      for (int i = 0; i < length; i++) {
        extracted(separator);
        final StackTraceElement stackTraceElement = stackTraceElements[i];
        CHARBUF.put((byte)'"');
        extracted(stackTraceElement.getClassName());
        CHARBUF.put((byte)'.');
        extracted(stackTraceElement.getMethodName());
        CHARBUF.put((byte)'(');
        //CHARBUF.append(stackTraceElement.getFileName());
        extracted(stackTraceElement.getFileName());
        CHARBUF.put((byte)':');
        //CHARBUF.append("stackTraceElement.getLineNumber()");
        extracted("stackTraceElement.getLineNumber()");
        CHARBUF.put((byte)')');
        CHARBUF.put((byte)'"');
        separator = ",";
      }
      CHARBUF.put((byte)']');
    }
  }
}
