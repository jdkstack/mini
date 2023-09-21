package org.jdkstack.logging.mini.core.tool;

/**
 * unboxing 拆箱.
 *
 * <p>Make unboxing explicit 明确拆箱(Object-->基本类型+String,StringBuilder).
 *
 * @author admin
 */
public class StringBuilderTool {

  public static void unbox(final StringBuilder sb, final Object arg) {
    if (arg == null) {
      //
    } else if (arg instanceof Integer) {
      sb.append(((Integer) arg).intValue());
    } else if (arg instanceof Short) {
      sb.append(((Short) arg).shortValue());
    } else if (arg instanceof String) {
      sb.append((String) arg);
    } else if (arg instanceof Long) {
      sb.append(((Long) arg).longValue());
    } else if (arg instanceof Character) {
      sb.append(((Character) arg).charValue());
    } else if (arg instanceof Double) {
      sb.append(((Double) arg).doubleValue());
    } else if (arg instanceof Float) {
      sb.append(((Float) arg).floatValue());
    } else if (arg instanceof Boolean) {
      sb.append(((Boolean) arg).booleanValue());
    } else if (arg instanceof Byte) {
      sb.append(((Byte) arg).byteValue());
    } else {
      sb.append((StringBuilder) arg);
    }
  }

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    long s = System.currentTimeMillis();
    for (int i = 0; i < 100000000; i++) {
      sb.setLength(0);
      unbox(sb, 2.98f);
    }
    long e = System.currentTimeMillis();
    System.out.println(e - s);
    try {
      Thread.sleep(100000);
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    }
  }
}
