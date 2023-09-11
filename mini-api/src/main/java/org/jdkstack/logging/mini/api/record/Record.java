package org.jdkstack.logging.mini.api.record;

import java.util.Map;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Record {

  Object[] getParams();

  void setParams(Object params, int index);

  int[] getPaths();

  void setPaths(int path, int index);

  int getArgsCount();

  void setArgsCount(int argsCount);

  int getUsedCount();

  void setUsedCount(int usedCount);

  int getPlaceholderCount();

  void setPlaceholderCount(int placeholderCount);

    /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getMessage();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @author admin
   */
  void setMessage(String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thrown .
   * @author admin
   */
  void setThrown(Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  String getLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @author admin
   */
  void setLevel(String logLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String 日志级别名称.
   * @author admin
   */
  String getLevelName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String.
   * @author admin
   */
  String getClassName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param className className.
   * @author admin
   */
  void setClassName(String className);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getYear();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param year .
   * @author admin
   */
  void setYear(long year);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getMonth();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param month .
   * @author admin
   */
  void setMonth(long month);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getDay();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param day .
   * @author admin
   */
  void setDay(long day);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getHours();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param hours .
   * @author admin
   */
  void setHours(long hours);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getMinute();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param minute .
   * @author admin
   */
  void setMinute(long minute);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getSecond();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param second .
   * @author admin
   */
  void setSecond(long second);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getMills();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param mills .
   * @author admin
   */
  void setMills(long mills);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getOffset();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param offset .
   * @author admin
   */
  void setOffset(String offset);

  String getLogLevel();

  void setLogLevel(String logLevel);

  String getEvent();

  void setEvent(String event);

  String getIngestion();

  void setIngestion(String ingestion);

  Throwable getThrowable();

  Map<String, String> getMap();

  void setMap(Map<String, String> map);

  String getProcess();

  void setProcess(String process);

  Object getArgs1();

  void setArgs1(Object args1);

  Object getArgs2();

  void setArgs2(Object args2);

  Object getArgs3();

  void setArgs3(Object args3);

  Object getArgs4();

  void setArgs4(Object args4);

  Object getArgs5();

  void setArgs5(Object args5);

  Object getArgs6();

  void setArgs6(Object args6);

  Object getArgs7();

  void setArgs7(Object args7);

  Object getArgs8();

  void setArgs8(Object args8);

  Object getArgs9();

  void setArgs9(Object args9);
}
