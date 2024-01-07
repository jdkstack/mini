package org.jdkstack.logging.mini.core.lz4;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import sun.misc.Unsafe;

public class UnsafeUtil {

  public static final Unsafe UNSAFE;
  private static final long ADDRESS_OFFSET;

  static {
    ByteOrder order = ByteOrder.nativeOrder();
    if (!order.equals(ByteOrder.LITTLE_ENDIAN)) {
      throw new RuntimeException("LZ4 requires a little endian platform");
    }

    try {
      Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
      theUnsafe.setAccessible(true);
      UNSAFE = (Unsafe) theUnsafe.get(null);
    } catch (Exception e) {
      throw new RuntimeException("LZ4 requires access to sun.misc.Unsafe");
    }

    try {
      // fetch the address field for direct buffers
      ADDRESS_OFFSET = UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("LZ4 requires access to java.nio.Buffer raw address field");
    }
  }

  public static long getAddress(Buffer buffer) {
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("buffer is not direct");
    }
    return UNSAFE.getLong(buffer, ADDRESS_OFFSET);
  }
}
