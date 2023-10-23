package org.jdkstack.logging.mini.core.datetime;

/**
 * This is a class description.
 *
 * <p>.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Constants {

  /** 一天24小时. */
  public static final int HOURS_PER_DAY = 24;
  /** 一小时60分钟. */
  public static final int MINUTES_PER_HOUR = 60;
  /** 一分钟60秒. */
  public static final int SECONDS_PER_MINUTE = 60;
  /** 一小时60*60秒. */
  public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
  /** 一天24*60*60秒. */
  public static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
  /** 一秒1000毫秒. */
  public static final long MILLS_PER_SECOND = 1000L;
  /** 一毫秒1000000纳秒. */
  public static final long NANOS_PER_MILLI = MILLS_PER_SECOND * 1000L;
  /** 一秒1000000000纳秒. */
  public static final long NANOS_PER_SECOND = 1000 * NANOS_PER_MILLI;
  /** 一分钟X纳秒. */
  public static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;
  /** 一小时X纳秒. */
  public static final long NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR;
  /** 一天X纳秒. */
  public static final long NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY;
  /** 365. */
  public static final long DAY = 365L;
  /** 在一种历法中,日期计算以400年为周期. */
  public static final long CYCLE = 400L;
  /**
   *
   *
   * <pre>
   * 400年周期内共有97个闰年,多一天. 闰年:四年一闰,百年不闰,四百年再闰.
   * ①能被4整除而不能被100整除.(如2004年就是闰年,1900年不是)
   * ②能被400整除.(如2000年不满足①，但是满足②，所以是闰年)
   * </pre>
   */
  public static final long LEAP = 97L;
  /** . */
  public static final long DAYS_PER_CYCLE1 = CYCLE * DAY;
  /** 每400年都有恰好146097天. */
  public static final long DAYS_PER_CYCLE = DAYS_PER_CYCLE1 + LEAP;
  /** 0000-2000多少天. */
  public static final long DAYS_0000_TO_2000 = DAYS_PER_CYCLE * 5L;
  /** . */
  public static final long N146097 = DAYS_PER_CYCLE;
  /** . */
  public static final long N146096 = DAYS_PER_CYCLE - 1;
  /** 1970-2000多少天,30年. */
  public static final long DAYS_30 = 30L * DAY;
  /** 1970-2000多少天,7个闰年. */
  public static final long DAYS_1970_TO_2000 = DAYS_30 + 7L;
  /** 0000-1970多少天,从1970年1月1日开始计算. */
  public static final long DAYS_0000_TO_1970 = DAYS_0000_TO_2000 - DAYS_1970_TO_2000;
  /** 0000-1970多少天,减去60天,从1970年3月1日开始计算. */
  public static final long DAYS_0000_TO_1970_MARCH_ONE = DAYS_0000_TO_1970 - 60;
  /** . */
  public static final int N0 = 0;
  /** . */
  public static final int N1 = 1;
  /** . */
  public static final int N2 = 2;
  /** . */
  public static final int N3 = 3;
  /** . */
  public static final int N4 = 4;
  /** . */
  public static final int N5 = 5;
  /** . */
  public static final int N6 = 6;
  /** . */
  public static final int N7 = 7;
  /** . */
  public static final int N8 = 8;
  /** . */
  public static final int N9 = 9;
  /** . */
  public static final int N10 = 10;
  /** . */
  public static final int N11 = 11;
  /** . */
  public static final int N12 = 12;
  /** . */
  public static final int N13 = 13;
  /** . */
  public static final int N14 = 14;
  /** . */
  public static final int N15 = 15;
  /** . */
  public static final int N16 = 16;
  /** . */
  public static final int N17 = 17;
  /** . */
  public static final int N18 = 18;
  /** . */
  public static final int N19 = 19;
  /** . */
  public static final int N20 = 20;
  /** . */
  public static final int N21 = 21;
  /** . */
  public static final int N22 = 22;
  /** . */
  public static final int N23 = 23;
  /** . */
  public static final int N28 = 28;
  /** . */
  public static final int N24 = 24;
  /** . */
  public static final int N100 = 100;
  /** . */
  public static final int N153 = 153;
  /** . */
  public static final int N256 = 256;
  /** . */
  public static final int N365 = 365;
  /** . */
  public static final int N399 = 399;
  /** . */
  public static final int N400 = 400;
  /** . */
  public static final int N60 = 60;
  /** . */
  public static final int N1000 = 1000;
  /** . */
  public static final int N1460 = 1460;
  /** . */
  public static final int N36524 = 36524;
  /** . */
  public static final int N9999 = 9999;
  /** . */
  public static final int N10000 = 10000;

  private Constants() {
    //
  }
}
