package org.jdkstack.logging.mini.core.algo.lz4;

import static org.jdkstack.logging.mini.core.algo.lz4.Lz4RawEncoder.MAX_TABLE_SIZE;
import static sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.jdkstack.logging.mini.api.lz4.Encoder;

/**
 * This class is not thread-safe
 * 数据压缩技术：在不丢失有用信息的前提下，通过相应的算法缩减信源数据冗余，从而提高数据存储、传输和处理效率的技术。
 * 无损压缩：利用数据的统计冗余进行压缩，常见的无损压缩编码方法有 Huffman编码，算术编码，LZ 编码（字典压缩）等。
 * 数据统计冗余度的理论限制为2:1到5:1，所以无损压缩的压缩比一般比较低。这类方法广泛应用于文本数据、程序等需要精确存储数据的压缩，
 * 有损压缩：利用了人类视觉、听觉对图像、声音中的某些频率成分不敏感的特性，允许压缩的过程中损失一定的信息，以此换来更大的压缩比。广泛应用于语音、图像和视频数据的压缩。
 */
public class Lz4Encoder implements Encoder {

  private final int[] table = new int[MAX_TABLE_SIZE];

  private static void verifyRange(byte[] data, int offset, int length) {
    if (data == null) {
      throw new NullPointerException("data is null");
    }
    if (0 > offset || 0 > length || offset + length > data.length) {
      throw new IllegalArgumentException("Invalid offset or length " + offset + ", " + offset + "in array of length " + data.length);
    }
  }

  @Override
  public void encode(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
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
    int written = Lz4RawEncoder.compress(inputBase, inputAddress, (int) (inputLimit - inputAddress), outputBase, outputAddress, outputLimit - outputAddress, table);
    output.position(output.position() + written);
  }
}
