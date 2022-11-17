package org.jdkstack.jdkringbuffer.core;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Power2 {

  private Power2() {
    //
  }

  /**
   * 计算2的下一次幂.
   *
   * <p>输入一个整数，根据整数计算出2的下一次幂.
   *
   * @param value 输入一个整数.
   * @return 2的下一次幂.
   * @author admin
   */
  public static int power2(final int value) {
    // 如果value为4，则newValue为3。
    final int newValue = value - 1;
    // 数字3的二进制是0000 0000 0000 0000 0000 0000 0000 0011。
    // 获取二进制数有多少个0，0有30个。
    final int count1 = Integer.numberOfLeadingZeros(newValue);
    // 获取二进制数有多少个非0，非0有2个。
    final int count2 = 32 - count1;
    // 返回2的s次方。
    return 1 << count2;
  }
}
