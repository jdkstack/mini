package org.jdkstack.logging.mini.core.formatter;

import org.jdkstack.logging.mini.core.pool.StringBuilderPool;

/**
 * 日志消息格式化.
 *
 * <p>用参数替换掉消息中的 "{}" 大括号对.
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

  private LogFormatter() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1) {
    return format(message,
        args1,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2) {
    return format(message,
        args1,
        args2,
        null,
        null,
        null,
        null,
        null,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3) {
    return format(message,
        args1,
        args2,
        args3,
        null,
        null,
        null,
        null,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @param args4   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4) {
    return format(message,
        args1,
        args2,
        args3,
        args4,
        null,
        null,
        null,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @param args4   .
   * @param args5   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5) {
    return format(message,
        args1,
        args2,
        args3,
        args4,
        args5,
        null,
        null,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @param args4   .
   * @param args5   .
   * @param args6   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6) {
    return format(message,
        args1,
        args2,
        args3,
        args4,
        args5,
        args6,
        null,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @param args4   .
   * @param args5   .
   * @param args6   .
   * @param args7   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7) {
    return format(message,
        args1,
        args2,
        args3,
        args4,
        args5,
        args6,
        args7,
        null,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @param args4   .
   * @param args5   .
   * @param args6   .
   * @param args7   .
   * @param args8   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8) {
    return format(message,
        args1,
        args2,
        args3,
        args4,
        args5,
        args6,
        args7,
        args8,
        null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @param args1   .
   * @param args2   .
   * @param args3   .
   * @param args4   .
   * @param args5   .
   * @param args6   .
   * @param args7   .
   * @param args8   .
   * @param args9   .
   * @return StringBuilder .
   * @author admin
   */
  public static StringBuilder format(
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8,
      final StringBuilder args9) {
    final StringBuilder sb = StringBuilderPool.poll();
    // 原始字符串长度.
    final int len = message.length();
    // 当前被处理的原始字符位置.
    int index = 0;
    // {} 个数.
    int braces = 0;
    // 循环处理每一个原始字符.
    while (index < len) {
      // 获取第一个原始字符.
      final char c = message.charAt(index);
      // 获取第二个原始字符.
      final int nextLen1 = index + 1;
      final char next;
      // 如果第二个字符超过了最大长度,则默认一个空字符，否则会越界.
      if (nextLen1 < len) {
        next = message.charAt(nextLen1);
      } else {
        next = ' ';
      }
      // 获取第三个原始字符.
      final char next1;
      final int nextLen2 = index + 2;
      // 如果第三个字符超过了最大长度,则默认一个空字符，否则会越界.
      if (nextLen2 < len) {
        next1 = message.charAt(nextLen2);
      } else {
        next1 = ' ';
      }
      // 如果当前字符和下一个字符正好是一对"{}"
      if ('{' == c && '}' == next) {
        hanlder(args1, args2, args3, args4, args5, args6, args7, args8, args9, sb, braces);
        // {}括号对计数.
        braces++;
        // 跳过'}'右括号.
        index++;
        // 如果当前字符和下一个字符,以及下一个字符正好是一对"{0}-{9}"
      } else if ('{' == c && '0' <= next && '9' >= next && '}' == next1) {
        hanlder(args1, args2, args3, args4, args5, args6, args7, args8, args9, sb, braces);
        // {}括号对计数.
        braces++;
        // 跳过'}'右括号.
        index++;
        // 跳过'0'-'9'.
        index++;
      } else {
        sb.append(c);
      }
      index++;
    }
    return sb;
  }

  private static void hanlder(
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8,
      final StringBuilder args9,
      final StringBuilder sb,
      final int braces) {
    if (Constants.N0 == braces) {
      // 拼接参数.
      sb.append(args1);
    } else if (Constants.N1 == braces) {
      // 拼接参数.
      sb.append(args2);
    } else if (Constants.N2 == braces) {
      // 拼接参数.
      sb.append(args3);
    } else if (Constants.N3 == braces) {
      // 拼接参数.
      sb.append(args4);
    } else if (Constants.N4 == braces) {
      // 拼接参数.
      sb.append(args5);
    } else if (Constants.N5 == braces) {
      // 拼接参数.
      sb.append(args6);
    } else if (Constants.N6 == braces) {
      // 拼接参数.
      sb.append(args7);
    } else if (Constants.N7 == braces) {
      // 拼接参数.
      sb.append(args8);
    } else {
      // 拼接参数.
      sb.append(args9);
    }
  }
}
