package org.jdkstack.logging.mini.api.record;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Record {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  StringBuilder getMessage();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @author admin
   */
  void setMessage(StringBuilder message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Throwable .
   * @author admin
   */
  Throwable getThrown();

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
   * @return String.
   * @author admin
   */
  String getClassMethod();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param classMethod classMethod.
   * @author admin
   */
  void setClassMethod(String classMethod);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int.
   * @author admin
   */
  int getLineNumber();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param lineNumber lineNumber.
   * @author admin
   */
  void setLineNumber(int lineNumber);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  StringBuilder getSb();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param sb .
   * @author admin
   */
  void setSb(StringBuilder sb);

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
}
