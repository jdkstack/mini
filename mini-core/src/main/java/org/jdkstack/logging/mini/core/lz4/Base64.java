package org.jdkstack.logging.mini.core.lz4;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

// 性能非常差，需要优化。
public class Base64 {

  private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
  private static final Map<Integer, Byte> maps = new HashMap<>();

  static {
    maps.put(0, (byte) 'A');
    maps.put(1, (byte) 'B');
    maps.put(2, (byte) 'C');
    maps.put(3, (byte) 'D');
    maps.put(4, (byte) 'E');
    maps.put(5, (byte) 'F');
    maps.put(6, (byte) 'G');
    maps.put(7, (byte) 'H');
    maps.put(8, (byte) 'I');
    maps.put(9, (byte) 'J');
    maps.put(10, (byte) 'K');
    maps.put(11, (byte) 'L');
    maps.put(12, (byte) 'M');
    maps.put(13, (byte) 'N');
    maps.put(14, (byte) 'O');
    maps.put(15, (byte) 'P');
    maps.put(16, (byte) 'Q');
    maps.put(17, (byte) 'R');
    maps.put(18, (byte) 'S');
    maps.put(19, (byte) 'T');
    maps.put(20, (byte) 'U');
    maps.put(21, (byte) 'V');
    maps.put(22, (byte) 'W');
    maps.put(23, (byte) 'X');
    maps.put(24, (byte) 'Y');
    maps.put(25, (byte) 'Z');
    maps.put(26, (byte) 'a');
    maps.put(27, (byte) 'b');
    maps.put(28, (byte) 'c');
    maps.put(29, (byte) 'd');
    maps.put(30, (byte) 'e');
    maps.put(31, (byte) 'f');
    maps.put(32, (byte) 'g');
    maps.put(33, (byte) 'h');
    maps.put(34, (byte) 'i');
    maps.put(35, (byte) 'j');
    maps.put(36, (byte) 'k');
    maps.put(37, (byte) 'l');
    maps.put(38, (byte) 'm');
    maps.put(39, (byte) 'n');
    maps.put(40, (byte) 'o');
    maps.put(41, (byte) 'p');
    maps.put(42, (byte) 'q');
    maps.put(43, (byte) 'r');
    maps.put(44, (byte) 's');
    maps.put(45, (byte) 't');
    maps.put(46, (byte) 'u');
    maps.put(47, (byte) 'v');
    maps.put(48, (byte) 'w');
    maps.put(49, (byte) 'x');
    maps.put(50, (byte) 'y');
    maps.put(51, (byte) 'z');
    maps.put(52, (byte) '0');
    maps.put(53, (byte) '1');
    maps.put(54, (byte) '2');
    maps.put(55, (byte) '3');
    maps.put(56, (byte) '4');
    maps.put(57, (byte) '5');
    maps.put(58, (byte) '6');
    maps.put(59, (byte) '7');
    maps.put(60, (byte) '8');
    maps.put(61, (byte) '9');
    maps.put(62, (byte) '+');
    maps.put(63, (byte) '/');
  }

  public static void encode(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
    int in = inputBuffer.limit();
    int b;
    for (int i = 0; i < in; i += 3) {
      // 252 11111100 0xfc
      //1. & 逻辑与运算，与运算的规则为：同时为1，结果为1，任意一方为0时，结果为0。
      //2. >> 右移运算。假设252右移2，则11111100变成00111111。
      // b的最大值是63，最小值是0， 0x3F  00111111 63 。
      // (inputBuffer.get(i) & 0xFC) 最大值是252，11111100，
      b = (inputBuffer.get(i) & 0xFC) >> 2;
      outputBuffer.put((byte) CODES.charAt(b));
      // 3     00000011  0x03
      //1. & 逻辑与运算，与运算的规则为：同时为1，结果为1，任意一方为0时，结果为0。
      //2. << 左移运算。假设3左移4，则00000011变成00110000。
      // b的最大值是48，0x30 00110000，最小值是0。
      // (inputBuffer.get(i) & 0x03) 最大值是3，最小值是0，
      b = (inputBuffer.get(i) & 0x03) << 4;
      if (i + 1 < in) {
        // 240  11110000  0xF0
        // 11110000-00001111
        //(inputBuffer.get(i + 1) & 0xF0) >> 4 最大值是00001111 ，15.
        // | 或运算，0-48 或运算 0-15
        // b的最大值是0-47
        b |= (inputBuffer.get(i + 1) & 0xF0) >> 4;
        outputBuffer.put((byte) CODES.charAt(b));
        // 15  00001111  0x0F
        // b 最大60
        b = (inputBuffer.get(i + 1) & 0x0F) << 2;
        if (i + 2 < in) {
          // 192  11000000  0xC0
          // b=3
          b |= (inputBuffer.get(i + 2) & 0xC0) >> 6;
          outputBuffer.put((byte) CODES.charAt(b));
          //63 00111111 0x3F
          b = inputBuffer.get(i + 2) & 0x3F;
          outputBuffer.put((byte) CODES.charAt(b));
        } else {
          outputBuffer.put((byte) CODES.charAt(b));
          outputBuffer.put((byte) '=');
        }
      } else {
        outputBuffer.put((byte) CODES.charAt(b));
        outputBuffer.put((byte) '=');
        outputBuffer.put((byte) '=');
      }
    }
    outputBuffer.flip();
  }

  public static void decode(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
    int in = inputBuffer.limit();
    if (in % 4 != 0) {
      throw new IllegalArgumentException("Invalid base64 input");
    }
    for (int i = 0; i < in; i += 4) {
      outputBuffer.put((byte) ((CODES.indexOf(inputBuffer.get(i)) << 2) | (CODES.indexOf(inputBuffer.get(i + 1)) >> 4)));
      if (CODES.indexOf(inputBuffer.get(i + 2)) < 64) {
        outputBuffer.put((byte) ((CODES.indexOf(inputBuffer.get(i + 1)) << 4) | (CODES.indexOf(inputBuffer.get(i + 2)) >> 2)));
        if (CODES.indexOf(inputBuffer.get(i + 3)) < 64) {
          outputBuffer.put((byte) ((CODES.indexOf(inputBuffer.get(i + 2)) << 6) | CODES.indexOf(inputBuffer.get(i + 3))));
        }
      }
    }
    outputBuffer.flip();
  }

  public static void encode2(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
    int in = inputBuffer.limit();
    int b;
    for (int i = 0; i < in; i += 3) {
      byte b1 = (byte) (inputBuffer.get(i) >> 2);
      byte b2 = (byte) (inputBuffer.get(i + 1) >> 2);
      byte b3 = (byte) (inputBuffer.get(i + 2) >> 2);

      // 252 11111100 0xfc
      //1. & 逻辑与运算，与运算的规则为：同时为1，结果为1，任意一方为0时，结果为0。
      //2. >> 右移运算。假设252右移2，则11111100变成00111111。
      // b的最大值是63，最小值是0， 0x3F  00111111 63 。
      // (inputBuffer.get(i) & 0xFC) 最大值是252，11111100，
      b = (inputBuffer.get(i) & 0xFC) >> 2;
      System.out.println(b);
      outputBuffer.put((byte) CODES.charAt(b));
      // 3     00000011  0x03
      //1. & 逻辑与运算，与运算的规则为：同时为1，结果为1，任意一方为0时，结果为0。
      //2. << 左移运算。假设3左移4，则00000011变成00110000。
      // b的最大值是48，0x30 00110000，最小值是0。
      // (inputBuffer.get(i) & 0x03) 最大值是3，最小值是0，
      b = (inputBuffer.get(i) & 0x03) << 4;
      if (i + 1 < in) {
        // 240  11110000  0xF0
        // 11110000-00001111
        //(inputBuffer.get(i + 1) & 0xF0) >> 4 最大值是00001111 ，15.
        // | 或运算，0-48 或运算 0-15
        // b的最大值是0-47
        b |= (inputBuffer.get(i + 1) & 0xF0) >> 4;
        outputBuffer.put((byte) CODES.charAt(b));
        // 15  00001111  0x0F
        // b 最大60
        b = (inputBuffer.get(i + 1) & 0x0F) << 2;
        if (i + 2 < in) {
          // 192  11000000  0xC0
          // b=3
          b |= (inputBuffer.get(i + 2) & 0xC0) >> 6;
          outputBuffer.put((byte) CODES.charAt(b));
          //63 00111111 0x3F
          b = inputBuffer.get(i + 2) & 0x3F;
          outputBuffer.put((byte) CODES.charAt(b));
        } else {
          outputBuffer.put((byte) CODES.charAt(b));
          outputBuffer.put((byte) '=');
        }
      } else {
        outputBuffer.put((byte) CODES.charAt(b));
        outputBuffer.put((byte) '=');
        outputBuffer.put((byte) '=');
      }
    }
    outputBuffer.flip();
  }
}
