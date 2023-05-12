package org.jdkstack.logging.mini.core.record;

import java.nio.CharBuffer;
import java.util.regex.Pattern;

/**
 * 日志消息格式化.
 *
 * <p>用参数替换掉消息中的 "{}" 大括号对.
 *
 * <pre>
 * 例如:
 *     1.LOG.info("{},{}", 1,2);
 *     2.日志消息是"{},{}"
 *     3.日志消息参数是1,2
 *     4.日志消息参数替换日志消息中的占位符{}
 * </pre>
 *
 * @author admin
 */
public final class LogFormatter {

  /** 采用模式匹配比字符串切割方法性能高. */
  private static final Pattern COMPILE = Pattern.compile("\\{}");
  /** 临时数组. */
  private static final CharBuffer charBuf =
      CharBuffer.allocate(org.jdkstack.logging.mini.core.codec.Constants.SOURCE);

  private LogFormatter() {
    //
  }

  /**
   * 日志消息格式化处理.
   *
   * @param message 日志消息.
   * @param args 日志消息参数.
   * @return 返回格式化后的日志消息.
   * @author admin
   */
  public static CharBuffer format(final String message, final String... args) {
    // 获取消息中大括号的数量,-1意思是严格进行分割,即使最后一个是空值.
    final String[] braces = COMPILE.split(message, -1);
    // 大括号的数量.
    final int braceCount = braces.length - 1;
    // 消息参数的数量.
    final int argsLen = args.length;
    // 没有大括号,没有参数,或者大括号数量比参数多.
    if (0 == braceCount || 0 == argsLen || braceCount > argsLen) {
      // return message;
    }
    // 原消息长度.
    final int length = message.length();
    // 格式化后的消息.
    // final StringBuilder sb = new StringBuilder(length);

    int total=0;
    // 清除缓存.
    charBuf.clear();
    // 将参数替换消息中的大括号.
    for (int i = 0; i < braceCount; i++) {
      // 拼接日志消息中{}前面的部分.
      String brace = braces[i];
      // 写入临时数组.
      brace.getChars(0, brace.length(), charBuf.array(), total);
      total += brace.length();
      // 拼接日志消息中{}对应的参数.
      String arg = args[i];
      // 写入临时数组.
      arg.getChars(0, arg.length(), charBuf.array(), total);
      total += arg.length();
    }
    // 结束读取的位置.
    charBuf.limit(total);
    // 开始读取的位置.
    charBuf.position(0);
    // 返回格式化后的日志消息.
    return charBuf;
  }

  public static void main(String[] args) {
    CharBuffer format =
        LogFormatter.format(
            "o{}ooo{}kk{}..{}m{}e{}ss{}ag{}e{}", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    System.out.println(new String(format.array()));
  }
}
