package org.jdkstack.logging.mini.core.lz4;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.jdkstack.logging.mini.core.lz4.UnsafeUtil.getAddress;
import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.jdkstack.logging.mini.api.lz4.Decoder;

/**
 * This class is not thread-safe
 */
public class Lz4Decoder implements Decoder {

  private static void verifyRange(byte[] data, int offset, int length) {
    requireNonNull(data, "data is null");
    if (0 > offset || 0 > length || offset + length > data.length) {
      throw new IllegalArgumentException(format("Invalid offset or length (%s, %s) in array of length %s", offset, length, data.length));
    }
  }

  @Override
  public void decode(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
    // Java 9+ added an overload of various methods in ByteBuffer. When compiling with Java 11+ and targeting Java 8 bytecode
    // the resulting signatures are invalid for JDK 8, so accesses below result in NoSuchMethodError. Accessing the
    // methods through the interface class works around the problem
    // Sidenote: we can't target "javac --release 8" because Unsafe is not available in the signature data for that profile
    Buffer input = inputBuffer;
    Buffer output = outputBuffer;

    Object inputBase;
    long inputAddress;
    long inputLimit;
    if (input.isDirect()) {
      inputBase = null;
      long address = getAddress(input);
      inputAddress = address + input.position();
      inputLimit = address + input.limit();
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
      outputBase = null;
      long address = getAddress(output);
      outputAddress = address + output.position();
      outputLimit = address + output.limit();
    } else if (output.hasArray()) {
      outputBase = output.array();
      outputAddress = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.position();
      outputLimit = ARRAY_BYTE_BASE_OFFSET + output.arrayOffset() + output.limit();
    } else {
      throw new IllegalArgumentException("Unsupported output ByteBuffer implementation " + output.getClass().getName());
    }

    // HACK: Assure JVM does not collect Slice wrappers while decompressing, since the
    // collection may trigger freeing of the underlying memory resulting in a segfault
    // There is no other known way to signal to the JVM that an object should not be
    // collected in a block, and technically, the JVM is allowed to eliminate these locks.
    int written = Lz4RawDecoder.decompress(inputBase, inputAddress, inputLimit, outputBase, outputAddress, outputLimit);
    output.position(output.position() + written);
  }
}
