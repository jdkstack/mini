package org.jdkstack.logging.mini.core.datetime;

import org.jdkstack.logging.mini.api.datetime.Decoder;


/**
 * <a href="https://howardhinnant.github.io/date_algorithms.html">Howard Hinnant</a>.
 *
 * <p>此工具可以表示公元纪年(0001-9999),公元前纪年(-N年-0000),公元后纪年(+10000-+N年).
 *
 * <p>目前仅支持0时区的日期时间格式转换成UTC毫秒.
 *
 * <pre>
 *   输入: 2022-11-05T23:05:01.105.000Z(输入的日期格式必须是Z时区,不支持其他时区)
 *   返回: 1667660701105(UTC毫秒)
 *   注意：不支持纳秒，必须含有毫秒。
 *
 *   ISO 8601日期表示法：
 *   公元纪年由4位数组成，公历公元1年为0001，月为2位数(01-12)，日为2位数(01-31).
 *   公元前纪年由-开头，公历公元前1年为0000，公历公元前2年为-0001，月为2位数(01-12)，日为2位数(01-31).
 *   公元后纪年由+开头，公历公元后1年为+10000，月为2位数(01-12)，日为2位数(01-31).
 *   ISO 8601时间表示法：
 *   小时(00-23)、分和秒都用2位数表示(00-59)、毫秒必须是3位数(000-999)。
 *   如UTC时间下午2点30分5秒表示为14:30:05.000Z(不支持其他表示)。
 *   ISO 8601日期和时间的组合表示法:
 *   合并表示时，要在时间前面加一大写字母T。
 *   如要表示UTC时间2004年5月3日下午5点30分8秒，可以写成2004-05-03T17:30:08.000Z。
 * </pre>
 *
 * @author admin
 */
public final class DateTimeDecoder implements Decoder {

  private DateTimeDecoder() {
    //
  }

  /**
   * 不支持时区.
   *
   * <p>offset计算目前存在阔年错误问题，暂时只支持offset=0。
   *
   * @param dateTime 必须是Z时区的日期时间格式.
   * @param offset   时区偏移量*3600秒(例如时区是+8:00,则offset=+8*3600).
   * @return long 返回1970年开始UTC时区的毫秒.
   * @author admin
   */
  public static long decoder(final StringBuilder dateTime, final long offset) {
    final long year = year(dateTime);
    final long mon = month(dateTime);
    final long day = day(dateTime);
    final long hour = hours(dateTime);
    final long min = minutes(dateTime);
    final long sed = seconds(dateTime);
    final long mis = millisecond(dateTime);
    final long era;
    if (0 <= year) {
      era = year / Constants.N400;
    } else {
      era = (year - Constants.N399) / Constants.N400;
    }
    final long doy;
    if (Constants.N2 < mon) {
      doy = (Constants.N153 * (mon - Constants.N3) + Constants.N2) / Constants.N5 + day - Constants.N1;
    } else {
      doy = (Constants.N153 * (mon + Constants.N9) + Constants.N2) / Constants.N5 + day - Constants.N1;
    }
    final long yoe = year - era * Constants.N400;
    final long doe = yoe * Constants.N365 + yoe / Constants.N4 - yoe / Constants.N100 + doy;
    // 总天数.
    final long dayCount = era * Constants.N146097 + doe - Constants.DAYS_0000_TO_1970_MARCH_ONE;
    // 总天数换算成毫秒(减去当前时区的秒数).
    long msSecond = (dayCount * Constants.N24 * Constants.N60 * Constants.N60 - offset) * Constants.N1000;
    // 小时换算成毫秒.
    msSecond += hour * Constants.N60 * Constants.N60 * Constants.N1000;
    // 分钟换算成毫秒.
    msSecond += min * Constants.N60 * Constants.N1000;
    // 秒换算成毫秒.
    msSecond += sed * Constants.N1000;
    // 加上毫秒.
    msSecond += mis;
    return msSecond;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long     .
   * @author admin
   */
  public static long decoder(final StringBuilder dateTime) {
    return decoder(dateTime, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long year(final StringBuilder dateTime) {
    return read(dateTime, Constants.N1);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long month(final StringBuilder dateTime) {
    return read(dateTime, Constants.N2);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long day(final StringBuilder dateTime) {
    return read(dateTime, Constants.N3);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long hours(final StringBuilder dateTime) {
    return read(dateTime, Constants.N4);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long minutes(final StringBuilder dateTime) {
    return read(dateTime, Constants.N5);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long seconds(final StringBuilder dateTime) {
    return read(dateTime, Constants.N6);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @return long .
   * @author admin
   */
  public static long millisecond(final StringBuilder dateTime) {
    return read(dateTime, Constants.N7);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @param type     .
   * @return long .
   * @author admin
   */
  public static long read(final StringBuilder dateTime, final int type) {
    // 当前被处理的原始字符位置.
    int index = 0;
    int x = 0;
    int y = 0;
    // 获取一个原始字符.
    final char c1 = dateTime.charAt(index);
    if ('+' == c1) {
      index++;
      x = 1;
    }
    if ('-' == c1) {
      index++;
      x = -1;
    }
    final int len = dateTime.length();
    long total = 0;
    long value = 0;
    // 0 开始遇到第一个-立马切换成1,代表年.
    // 1
    int state = 0;
    // 循环处理每一个原始字符.
    while (index < len) {
      // 获取一个原始字符.
      final char c = dateTime.charAt(index);
      switch (c) {
        case 'T':
          if (2 == state) {
            // 月结束,日开始.
            value = total;
            total = 0;
            state = 3;
            if (3 == type) {
              index = len;
            }
          }
          break;
        case ':':
          if (3 == state) {
            // 小时结束,分钟开始.
            value = total;
            total = 0;
            state = 4;
            if (4 == type) {
              index = len;
            }
          } else if (4 == state) {
            // 分钟结束,秒开始.
            value = total;
            total = 0;
            state = 5;
            if (5 == type) {
              index = len;
            }
          } else if (7 == state) {
            // 时区小时开始.
            total = total * 3600;
            value = total;
            total = 0;
            state = 8;
          }
          break;
        case '.':
          if (5 == state) {
            // 秒结束,毫秒开始.
            value = total;
            total = 0;
            state = 6;
            if (6 == type) {
              index = len;
            }
          }
          break;
        case '+':
          if (6 == state) {
            // 毫秒结束,时区开始.
            y = 1;
            value = total;
            total = 0;
            state = 7;
            if (7 == type) {
              index = len;
            }
          }
          break;
        case '-':
          if (0 == state) {
            // 年结束,月开始.
            state = 1;
            if (1 == x) {
              value = +total;
            }
            if (-1 == x) {
              value = -total;
            }
            if (0 == x) {
              value = +total;
            }
            total = 0;
            if (1 == type) {
              index = len;
            }
          } else if (1 == state) {
            // 月结束,日开始.
            state = 2;
            value = total;
            total = 0;
            if (2 == type) {
              index = len;
            }
          } else if (6 == state) {
            // 毫秒结束,时区开始.
            y = -1;
            state = 7;
            value = total;
            total = 0;
            if (7 == type) {
              index = len;
            }
          }
          break;
        case 'Z':
          if (6 == state) {
            // 毫秒结束,时区开始.
            y = 0;
            state = 10;
            // 时区结束.
            value = total;
            total = 0;
            if (7 == type) {
              index = len;
            }
          }
          break;
        default:
          final long temp1 = total * 10;
          total = temp1 + (c - '0');
          break;
      }
      index++;
    }
    if (10 == state) {
      value = value;
    }
    if (8 == state) {
      // 时区分钟开始.
      total = total * 60;
      // 时区结束.
      value = value + total;
      total = 0;
      if (8 == type) {
        index = len;
      }
      if (1 == y) {
        value = value;
      }
      if (-1 == y) {
        value = -value;
      }
      if (0 == y) {
        value = 0;
      }
    }
    return value;
  }
}
