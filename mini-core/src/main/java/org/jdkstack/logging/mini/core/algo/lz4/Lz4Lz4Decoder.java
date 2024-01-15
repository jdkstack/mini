package org.jdkstack.logging.mini.core.algo.lz4;

import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * This class is not thread-safe
 */
public class Lz4Lz4Decoder implements org.jdkstack.logging.mini.api.lz4.Lz4Decoder {

  private static void verifyRange(byte[] data, int offset, int length) {
    if (data == null) {
      throw new NullPointerException("data is null");
    }
    if (0 > offset || 0 > length || offset + length > data.length) {
      throw new IllegalArgumentException("Invalid offset or length " + offset + ", " + offset + "in array of length " + data.length);
    }
  }

  @Override
  public void decode(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
    Buffer input = inputBuffer;
    Buffer output = outputBuffer;

    Object inputBase;
    long inputAddress;
    long inputLimit;
    if (input.isDirect()) {
      throw new IllegalArgumentException("Unsupported buffer is direct");
    } else if (input.hasArray()) {
      inputBase = input.array();
      inputAddress = ARRAY_BYTE_BASE_OFFSET + input.arrayOffset() + input.position();
      inputLimit = ARRAY_BYTE_BASE_OFFSET + input.arrayOffset() + input.limit();
    } else {
      throw new IllegalArgumentException("Unsupported input ByteBuffer implementation " + input.getClass().getName());
    }

    Object outputBase;
    long outputAddress;
    long outputLimit;
    if (output.isDirect()) {
      throw new IllegalArgumentException("Unsupported buffer is direct");
    } else if (output.hasArray()) {
      outputBase = output.array();
      outputAddress = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.position();
      outputLimit = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.limit();
    } else {
      throw new IllegalArgumentException("Unsupported output ByteBuffer implementation " + output.getClass().getName());
    }
    int written = Lz4RawDecoder.decompress(inputBase, inputAddress, inputLimit, outputBase, outputAddress, outputLimit);
    output.position(output.position() + written);
  }
}
