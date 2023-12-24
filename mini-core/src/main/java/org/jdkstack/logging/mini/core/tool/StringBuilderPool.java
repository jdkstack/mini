package org.jdkstack.logging.mini.core.tool;

import org.jdkstack.logging.mini.core.thread.LogProduceThread;

/**
 * boxing 装箱.
 *
 * <p>Make boxing explicit 明确装箱(基本类型转成StringBuilder,StringBuilder转换成Object).
 *
 * @author admin
 */
public class StringBuilderPool {

  private StringBuilderPool() {
    //
  }

  public static StringBuilder box(final float value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final double value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final short value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final int value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final char value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final long value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final byte value) {
    return getStringBuilder().append(value);
  }

  public static StringBuilder box(final boolean value) {
    return getStringBuilder().append(value);
  }

  private static StringBuilder getStringBuilder() {
    // 当前线程是日志库提供的吗?
    LogProduceThread logProduceThread = ThreadLocalTool.getLogProduceThread();
    return logProduceThread.poll();
  }
}
