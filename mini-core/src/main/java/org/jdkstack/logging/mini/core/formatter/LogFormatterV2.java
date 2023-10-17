package org.jdkstack.logging.mini.core.formatter;

import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.tool.StringBuilderTool;

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
public final class LogFormatterV2 {

  private LogFormatterV2() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message message.
   * @param lr lr.
   * @author admin
   */
  public static void format(final Record lr, final String message) {
    // 1.计算 {} 在message中的最短路径.
    final int length = message.length();
    // {} 总数量.
    int placeholderCount = 0;
    // 循环message每一个字符.
    for (int i = 0; i < length - 1; i++) {
      // 得到当前字符.
      final char curChar = message.charAt(i);
      // 判断当前字符是不是{，以及下一个字符是不是}.
      if (curChar == '{' && message.charAt(i + 1) == '}') {
        // 每个{}的开始位置.
        lr.setPaths(i, placeholderCount);
        // 下一个括号.
        placeholderCount++;
        // 下一个字符.
        i++;
      }
    }
    // 2.根据最短路径来拼接参数.
    final StringBuilder sb = lr.getMessageText();
    // 得到参数列表.
    final Object[] params = lr.getParams();
    // 得到最短路径坐标.
    final int[] paths = lr.getPaths();
    // 当前{}的坐标位置.
    int previous = 0;
    // 有多少{},循环多少次.
    for (int i = 0; i < placeholderCount; i++) {
      // 先拼接第一个{}之前的部分message.
      sb.append(message, previous, paths[i]);
      // 下一个{}的坐标位置.
      previous = paths[i] + 2;
      // 拼接对应{}的参数.
      StringBuilderTool.unbox(sb, params[i]);
    }
    // 拼接最后一个{}到message最后部分.
    sb.append(message, previous, length);
  }
}
