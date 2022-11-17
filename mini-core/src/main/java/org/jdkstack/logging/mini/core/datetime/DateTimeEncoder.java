package org.jdkstack.logging.mini.core.datetime;

import org.jdkstack.logging.mini.api.datetime.Encoder;
import org.jdkstack.logging.mini.core.pool.StringBuilderPool;


/**
 * <a href="https://howardhinnant.github.io/date_algorithms.html">Howard Hinnant</a>.
 *
 * <p>此工具可以表示公元纪年（0001-9999）,公元前纪年(-N年-0000)，公元后纪年(+10000-+N年).
 *
 * <p>目前仅支持1970年后的UTC毫秒转换成Z时区的日期时间格式.
 *
 * <pre>
 *   输入: 1667660701105(输入必须1970年后的UTC毫秒)
 *   返回: 2022-11-05T23:05:01.105Z(输出的日期格式必须是Z时区,不支持其他时区)
 *
 *   ISO 8601日期表示法：
 *   公元纪年由4位数组成，公历公元1年为0001，月为2位数(01-12)，日为2位数(01-31).
 *   公元前纪年由-开头，公历公元前1年为0000，月为2位数(01-12)，日为2位数(01-31).
 *   公元后纪年由+开头，公历公元后1年为+10000，月为2位数(01-12)，日为2位数(01-31).
 *   ISO 8601时间表示法：
 *   小时(00-23)、分和秒都用2位数表示(00-59)、毫秒必须是3位数(000-999)。
 *   如UTC时间下午2点30分5秒表示为14:30:05Z(不支持其他表示)。
 *   ISO 8601日期和时间的组合表示法:
 *   合并表示时，要在时间前面加一大写字母T。
 *   如要表示UTC时间2004年5月3日下午5点30分8秒，可以写成2004-05-03T17:30:08Z。
 * </pre>
 *
 * @author admin
 */
public final class DateTimeEncoder implements Encoder {

  private DateTimeEncoder() {
    //
  }


  /**
   * 忽略了本地时区(生成了大量的临时对象).
   *
   * <p>offset计算目前存在阔年错误问题，暂时只支持offset=0。
   *
   * @param millis 必须是1970年开始UTC时区的毫秒.
   * @param offset 时区偏移量*3600秒(例如时区是+8:00,则offset=+8*3600).
   * @return StringBuilder 返回UTC Z时区的日期时间格式.
   * @author admin
   */
  public static StringBuilder encoder(final long millis, final long offset) {
    final StringBuilder sb = StringBuilderPool.poll();
    // millis/1000得到秒.
    final long secondUtc = millis / Constants.MILLS_PER_SECOND;
    //  本地总秒数.
    final long localSecond = secondUtc + offset;
    // 1970年到现在的天数,一天86400秒,计算一下恰好有多少天.
    final long todayTo1970 = Math.floorDiv(localSecond, Constants.SECONDS_PER_DAY);
    //0000-1970年的天数,加上1970-现在的天数，0000年到现在的总天数。
    final long zeroToToday = todayTo1970 + Constants.DAYS_0000_TO_1970;
    // 从三月一号开始的天数(1月+2月=59天).
    final long zeroToTodayFrom3 = zeroToToday - 60;
    // N个四百年.
    final long era;
    if (0 <= zeroToTodayFrom3) {
      // 总天数/400年的天數=N四百年.
      era = zeroToTodayFrom3 / Constants.N146097;
    } else {
      // 如果是负的，表示公元前.
      era = (zeroToTodayFrom3 - Constants.N146096) / Constants.N146097;
    }
    // N个四百年的总天数.
    final long temp1 = era * Constants.N146097;
    // 减去N个四百年的总天数,得到剩余的天数.
    final long doe = zeroToTodayFrom3 - temp1;
    // 1460 = 365 * 4， 第4年会多出1天（闰年）.
    final long temp3 = doe / Constants.N1460;
    // 35624 = 365 * 100 + 100 / 4 - 1，第100年不是闰年.
    final long temp4 = doe / Constants.N36524;
    // 146096 = 365 * 400 + 400 / 4 - 400 / 100，第400年（闰年）.
    final long temp5 = doe / Constants.N146096;
    // 剩余的天数减去所有闰年.
    final long temp6 = doe - temp3 + temp4 - temp5;
    // 最后剩下的天数/365.
    final long yoe = temp6 / Constants.N365;
    //得到yp、mp、day（即基于03-01是第一天的值）
    final long yp = era * Constants.N400;
    final long year = yoe + yp;
    check3(sb, year);
    final long doy = doe - (Constants.N365 * yoe + yoe / 4 - yoe / Constants.N100);
    final long mp = (5 * doy + 2) / Constants.N153;
    final long month;
    if (Constants.N10 > mp) {
      month = mp + Constants.N3;
    } else {
      month = mp - Constants.N9;
    }
    check(sb, month, '-');
    final long day = doy - (Constants.N153 * mp + 2) / 5 + 1;
    check(sb, day, '-');
    sb.append('T');
    // 还余下多少秒.
    final int secondOfDay = Math.floorMod(localSecond, Constants.SECONDS_PER_DAY);
    // 将余下的秒转成纳秒.
    final long nanoSecondDay = secondOfDay * Constants.NANOS_PER_SECOND;
    // millis%1000得到毫秒(减去秒).
    final long millisecondUtc = millis % Constants.MILLS_PER_SECOND;
    // millisecondUTC转换成纳秒.
    final long nanoSecondUtc = millisecondUtc * Constants.NANOS_PER_MILLI;
    // 总纳秒数.
    long nanoOfDay = nanoSecondDay + nanoSecondUtc;
    final long hour = nanoOfDay / Constants.NANOS_PER_HOUR;
    nanoOfDay -= hour * Constants.NANOS_PER_HOUR;
    final long minute = nanoOfDay / Constants.NANOS_PER_MINUTE;
    nanoOfDay -= minute * Constants.NANOS_PER_MINUTE;
    final long second = nanoOfDay / Constants.NANOS_PER_SECOND;
    nanoOfDay -= second * Constants.NANOS_PER_SECOND;
    final long millisecond = nanoOfDay / Constants.NANOS_PER_MILLI;
    check10(sb, hour);
    check(sb, minute, ':');
    check(sb, second, ':');
    check2(sb, millisecond);
    final long h = offset / 3_600;
    final long s = offset % 3_600;
    final long m = s / 60;
    if (0 < offset) {
      sb.append('+');
      if (10 > h) {
        sb.append('0');
        sb.append(h);
      } else {
        sb.append(h);
      }
      sb.append(':');
      if (10 > m) {
        sb.append('0');
        sb.append(m);
      } else {
        sb.append(m);
      }
    } else if (0 > offset) {
      if (-10 > h) {
        sb.append(h);
      } else {
        sb.append('0');
        sb.append(h);
      }
      sb.append(':');
      if (-10 > m) {
        sb.append(m);
      } else {
        sb.append('0');
        sb.append(m);
      }
    } else {
      sb.append('Z');
    }
    return sb;
  }

  public static StringBuilder encoder(final long millis) {
    return encoder(millis, 0);
  }

  private static void check3(final StringBuilder sb, final long year) {
    if (Constants.N1000 > year) {
      if (0 > year) {
        if (-Constants.N9999 > year) {
          sb.append(year);
        } else {
          //小于0代表公元前年，加入year是-100，则-10100去掉第二个字符后就是公元前-0100年.
          sb.append(year - Constants.N10000).deleteCharAt(1);
        }
      } else {
        //大于0小于1000,加入year是500，则10500,去掉第一个字符后就是公元0500年.
        sb.append(year + Constants.N10000).deleteCharAt(0);
      }
    } else {
      //大于9999,代表公元后10000元,表示为+10000.
      if (Constants.N9999 < year) {
        sb.append('+');
      }
      //小于9999,代表正常的公元9999年.
      sb.append(year);
    }
  }

  private static void check2(final StringBuilder sb, final long value) {
    sb.append('.');
    if (Constants.N10 > value) {
      sb.append("00");
    } else if (Constants.N100 > value) {
      sb.append('0');
    }
    sb.append(value);
  }

  private static void check10(final StringBuilder sb, final long value) {
    if (Constants.N10 > value) {
      sb.append('0');
    }
    sb.append(value);
  }

  private static void check(final StringBuilder sb, final long value, final char symbol) {
    sb.append(symbol);
    if (Constants.N10 > value) {
      sb.append('0');
    }
    sb.append(value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long year(final long millis, final long offset) {
    final long epochSecond = millis / 1000;
    final long localSecond = epochSecond + offset;
    final long localEpochDay = Math.floorDiv(localSecond, Constants.SECONDS_PER_DAY);
    final long zeroDay = localEpochDay + Constants.DAYS_0000_TO_1970 - 60;
    final long era;
    if (0 <= zeroDay) {
      era = zeroDay / Constants.N146097;
    } else {
      era = (zeroDay - Constants.N146096) / Constants.N146097;
    }
    final long doe = zeroDay - era * Constants.N146097;
    final long yoe = (doe - doe / Constants.N1460 + doe / Constants.N36524 - doe / Constants.N146096) / Constants.N365;
    return yoe + era * Constants.N400;
  }

  public static long year(final long millis) {
    return year(millis, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long month(final long millis, final long offset) {
    final long epochSecond = millis / 1000;
    final long localSecond = epochSecond + offset;
    final long localEpochDay = Math.floorDiv(localSecond, Constants.SECONDS_PER_DAY);
    final long zeroDay = localEpochDay + Constants.DAYS_0000_TO_1970 - 60;
    final long era;
    if (0 <= zeroDay) {
      era = zeroDay / Constants.N146097;
    } else {
      era = (zeroDay - Constants.N146096) / Constants.N146097;
    }
    final long doe = zeroDay - era * Constants.N146097;
    final long yoe = (doe - doe / Constants.N1460 + doe / Constants.N36524 - doe / Constants.N146096) / Constants.N365;
    final long doy = doe - (Constants.N365 * yoe + yoe / Constants.N4 - yoe / Constants.N100);
    final long mp = (Constants.N5 * doy + Constants.N2) / Constants.N153;
    if (Constants.N10 > mp) {
      return mp + Constants.N3;
    }
    return mp - Constants.N9;
  }

  public static long month(final long millis) {
    return month(millis, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long day(final long millis, final long offset) {
    final long epochSecond = millis / 1000;
    final long localSecond = epochSecond + offset;
    final long localEpochDay = Math.floorDiv(localSecond, Constants.SECONDS_PER_DAY);
    final long zeroDay = localEpochDay + Constants.DAYS_0000_TO_1970 - 60;
    final long era;
    if (Constants.N0 <= zeroDay) {
      era = zeroDay / Constants.N146097;
    } else {
      era = (zeroDay - Constants.N146096) / Constants.N146097;
    }
    final long doe = zeroDay - era * Constants.N146097;
    final long yoe = (doe - doe / Constants.N1460 + doe / Constants.N36524 - doe / Constants.N146096) / Constants.N365;
    final long doy = doe - (Constants.N365 * yoe + yoe / Constants.N4 - yoe / Constants.N100);
    final long mp = (Constants.N5 * doy + Constants.N2) / Constants.N153;
    return doy - (Constants.N153 * mp + Constants.N2) / Constants.N5 + Constants.N1;
  }

  public static long day(final long millis) {
    return day(millis, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long hours(final long millis, final long offset) {
    final long epochSecond = millis / Constants.N1000;
    final long nanoOfSecond = millis % Constants.N1000 * Constants.NANOS_PER_MILLI;
    final long localSecond = epochSecond + offset;
    final int secsOfDay = Math.floorMod(localSecond, Constants.SECONDS_PER_DAY);
    final long nanoOfDay = secsOfDay * Constants.NANOS_PER_SECOND + nanoOfSecond;
    return nanoOfDay / Constants.NANOS_PER_HOUR;
  }

  public static long hours(final long millis) {
    return hours(millis, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long minutes(final long millis, final long offset) {
    final long epochSecond = millis / Constants.N1000;
    final long nanoOfSecond = millis % Constants.N1000 * Constants.NANOS_PER_MILLI;
    final long localSecond = epochSecond + offset;
    final int secsOfDay = Math.floorMod(localSecond, Constants.SECONDS_PER_DAY);
    long nanoOfDay = secsOfDay * Constants.NANOS_PER_SECOND + nanoOfSecond;
    final long hours = nanoOfDay / Constants.NANOS_PER_HOUR;
    nanoOfDay -= hours * Constants.NANOS_PER_HOUR;
    return nanoOfDay / Constants.NANOS_PER_MINUTE;
  }

  public static long minutes(final long millis) {
    return minutes(millis, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long seconds(final long millis, final long offset) {
    final long epochSecond = millis / Constants.N1000;
    final long nanoOfSecond = millis % Constants.N1000 * Constants.NANOS_PER_MILLI;
    final long localSecond = epochSecond + offset;
    final int secsOfDay = Math.floorMod(localSecond, Constants.SECONDS_PER_DAY);
    long nanoOfDay = secsOfDay * Constants.NANOS_PER_SECOND + nanoOfSecond;
    final long hours = nanoOfDay / Constants.NANOS_PER_HOUR;
    nanoOfDay -= hours * Constants.NANOS_PER_HOUR;
    final long minutes = nanoOfDay / Constants.NANOS_PER_MINUTE;
    nanoOfDay -= minutes * Constants.NANOS_PER_MINUTE;
    return nanoOfDay / Constants.NANOS_PER_SECOND;
  }

  public static long seconds(final long millis) {
    return seconds(millis, 0);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param millis .
   * @return long .
   * @author admin
   */
  public static long millisecond(final long millis, final long offset) {
    final long epochSecond = millis / Constants.N1000;
    final long nanoOfSecond = millis % Constants.N1000 * Constants.NANOS_PER_MILLI;
    final long localSecond = epochSecond + offset;
    final int secsOfDay = Math.floorMod(localSecond, Constants.SECONDS_PER_DAY);
    long nanoOfDay = secsOfDay * Constants.NANOS_PER_SECOND + nanoOfSecond;
    final long hours = nanoOfDay / Constants.NANOS_PER_HOUR;
    nanoOfDay -= hours * Constants.NANOS_PER_HOUR;
    final long minutes = nanoOfDay / Constants.NANOS_PER_MINUTE;
    nanoOfDay -= minutes * Constants.NANOS_PER_MINUTE;
    final long seconds = nanoOfDay / Constants.NANOS_PER_SECOND;
    nanoOfDay -= seconds * Constants.NANOS_PER_SECOND;
    return nanoOfDay / Constants.NANOS_PER_MILLI;
  }

  public static long millisecond(final long millis) {
    return millisecond(millis, 0);
  }
}
