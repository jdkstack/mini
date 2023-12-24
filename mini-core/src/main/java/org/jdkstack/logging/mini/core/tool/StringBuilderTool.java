package org.jdkstack.logging.mini.core.tool;

/**
 * unboxing 拆箱.
 *
 * <p>Make unboxing explicit 明确拆箱(Object转换成StringBuilder，StringBuilder不需要转换成基本类型).
 *
 * @author admin
 */
public class StringBuilderTool {

  private StringBuilderTool() {
    //
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param sb  .
   * @param arg .
   * @author admin
   */
  public static void unbox(final StringBuilder sb, final Object arg) {
    if (arg == null) {
      //
    } else {
      sb.append((StringBuilder) arg);
    }
  }
}
