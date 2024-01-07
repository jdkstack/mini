package org.jdkstack.logging.mini.core.lz4;

import static org.jdkstack.logging.mini.core.lz4.UnsafeUtil.UNSAFE;

public class Lz4RawDecoder {

  public static final int LAST_LITERAL_SIZE = 5;
  public static final int MIN_MATCH = 4;
  public static final int SIZE_OF_SHORT = 2;
  public static final int SIZE_OF_INT = 4;
  public static final int SIZE_OF_LONG = 8;
  private static final int[] DEC_32_TABLE = {4, 1, 2, 1, 4, 4, 4, 4};
  private static final int[] DEC_64_TABLE = {0, 0, 0, -1, 0, 1, 2, 3};

  private static final int OFFSET_SIZE = 2;
  private static final int TOKEN_SIZE = 1;

  public static int decompress(final Object inputBase, final long inputAddress, final long inputLimit, final Object outputBase, final long outputAddress, final long outputLimit) {
    final long fastOutputLimit = outputLimit - SIZE_OF_LONG; // maximum offset in output buffer to which it's safe to write long-at-a-time

    long input = inputAddress;
    long output = outputAddress;

    if (inputAddress == inputLimit) {
      throw new DecodeException(0, "input is empty");
    }

    if (outputAddress == outputLimit) {
      if (1 == inputLimit - inputAddress && 0 == UNSAFE.getByte(inputBase, inputAddress)) {
        return 0;
      }
      return -1;
    }

    while (input < inputLimit) {
      final int token = UNSAFE.getByte(inputBase, input) & 0xFF;
      input++;

      // decode literal length
      int literalLength = token >>> 4; // top-most 4 bits of token
      if (0xF == literalLength) {
        int value;
        do {
          value = UNSAFE.getByte(inputBase, input) & 0xFF;
          input++;
          literalLength += value;
        } while (255 == value && input < inputLimit - 15);
      }

      // copy literal
      long literalEnd = input + literalLength;
      long literalOutputLimit = output + literalLength;
      if (literalOutputLimit > fastOutputLimit - MIN_MATCH || literalEnd > inputLimit - (OFFSET_SIZE + TOKEN_SIZE + LAST_LITERAL_SIZE)) {
        // copy the last literal and finish
        if (literalOutputLimit > outputLimit) {
          throw new DecodeException(input - inputAddress, "attempt to write last literal outside of destination buffer");
        }

        if (literalEnd != inputLimit) {
          throw new DecodeException(input - inputAddress, "all input must be consumed");
        }

        // slow, precise copy
        UNSAFE.copyMemory(inputBase, input, outputBase, output, literalLength);
        output += literalLength;
        break;
      }

      // fast copy. We may overcopy but there's enough room in input and output to not overrun them
      int index = 0;
      do {
        UNSAFE.putLong(outputBase, output, UNSAFE.getLong(inputBase, input));
        output += SIZE_OF_LONG;
        input += SIZE_OF_LONG;
        index += SIZE_OF_LONG;
      } while (index < literalLength);
      output = literalOutputLimit;

      input = literalEnd;

      // get offset
      // we know we can read two bytes because of the bounds check performed before copying the literal above
      int offset = UNSAFE.getShort(inputBase, input) & 0xFFFF;
      input += SIZE_OF_SHORT;

      long matchAddress = output - offset;
      if (matchAddress < outputAddress) {
        throw new DecodeException(input - inputAddress, "offset outside destination buffer");
      }

      // compute match length
      int matchLength = token & 0xF; // bottom-most 4 bits of token
      if (0xF == matchLength) {
        int value;
        do {
          if (input > inputLimit - LAST_LITERAL_SIZE) {
            throw new DecodeException(input - inputAddress);
          }

          value = UNSAFE.getByte(inputBase, input) & 0xFF;
          input++;
          matchLength += value;
        } while (255 == value);
      }
      matchLength += MIN_MATCH; // implicit length from initial 4-byte match in encoder

      long matchOutputLimit = output + matchLength;

      // at this point we have at least 12 bytes of space in the output buffer
      // due to the fastLimit check before copying a literal, so no need to check again

      // copy repeated sequence
      if (SIZE_OF_LONG > offset) {
        // 8 bytes apart so that we can copy long-at-a-time below
        int increment32 = DEC_32_TABLE[offset];
        int decrement64 = DEC_64_TABLE[offset];

        UNSAFE.putByte(outputBase, output, UNSAFE.getByte(outputBase, matchAddress));
        UNSAFE.putByte(outputBase, output + 1, UNSAFE.getByte(outputBase, matchAddress + 1));
        UNSAFE.putByte(outputBase, output + 2, UNSAFE.getByte(outputBase, matchAddress + 2));
        UNSAFE.putByte(outputBase, output + 3, UNSAFE.getByte(outputBase, matchAddress + 3));
        output += SIZE_OF_INT;
        matchAddress += increment32;

        UNSAFE.putInt(outputBase, output, UNSAFE.getInt(outputBase, matchAddress));
        output += SIZE_OF_INT;
        matchAddress -= decrement64;
      } else {
        UNSAFE.putLong(outputBase, output, UNSAFE.getLong(outputBase, matchAddress));
        matchAddress += SIZE_OF_LONG;
        output += SIZE_OF_LONG;
      }

      if (matchOutputLimit > fastOutputLimit - MIN_MATCH) {
        if (matchOutputLimit > outputLimit - LAST_LITERAL_SIZE) {
          throw new DecodeException(input - inputAddress, String.format("last %s bytes must be literals", LAST_LITERAL_SIZE));
        }

        while (output < fastOutputLimit) {
          UNSAFE.putLong(outputBase, output, UNSAFE.getLong(outputBase, matchAddress));
          matchAddress += SIZE_OF_LONG;
          output += SIZE_OF_LONG;
        }

        while (output < matchOutputLimit) {
          UNSAFE.putByte(outputBase, output, UNSAFE.getByte(outputBase, matchAddress));
          output++;
          matchAddress++;
        }
      } else {
        int i = 0;
        do {
          UNSAFE.putLong(outputBase, output, UNSAFE.getLong(outputBase, matchAddress));
          output += SIZE_OF_LONG;
          matchAddress += SIZE_OF_LONG;
          i += SIZE_OF_LONG;
        } while (i < matchLength - SIZE_OF_LONG); // first long copied previously
      }

      output = matchOutputLimit; // correction in case we overcopied
    }

    return (int) (output - outputAddress);
  }
}
