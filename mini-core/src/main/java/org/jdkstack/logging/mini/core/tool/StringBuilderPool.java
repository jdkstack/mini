package org.jdkstack.logging.mini.core.tool;

/**
 * boxing 装箱.
 *
 * <p>Make boxing explicit 明确装箱(基本类型+String,StringBuilder->Object).
 *
 * @author admin
 */
public class StringBuilderPool {
  private static final int CAPACITY = 1024;
  private static final int MASK = CAPACITY - 1;

  private static class State {
    private final StringBuilder[] ringBuffer = new StringBuilder[CAPACITY];
    private int current;

    State() {
      for (int i = 0; i < ringBuffer.length; i++) {
        ringBuffer[i] = new StringBuilder(21);
      }
    }

    public StringBuilder poll() {
      final StringBuilder result = ringBuffer[MASK & current++];
      result.setLength(0);
      return result;
    }
  }

  private static ThreadLocal<State> threadLocalState = new ThreadLocal<>();

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

  private static State getState() {
    State state = threadLocalState.get();
    if (state == null) {
      state = new State();
      threadLocalState.set(state);
    }
    return state;
  }

  private static StringBuilder getStringBuilder() {
    return getState().poll();
  }
}
