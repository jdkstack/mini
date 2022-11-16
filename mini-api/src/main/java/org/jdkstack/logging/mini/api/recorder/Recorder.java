package org.jdkstack.logging.mini.api.recorder;

import org.jdkstack.logging.mini.api.level.Level;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Recorder {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getType();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key     .
   * @param handler .
   * @author admin
   */
  void addHandlers(String key, String handler);

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @return String .
   * @author admin
   */
  String getHandler(String key);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevels logLevels.
   * @return boolean
   * @author admin
   */
  boolean doFilter(String logLevels);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler handler.
   * @author admin
   */
  void removeHandler(String handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  Level getMinLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param minLevel minLevel.
   * @author admin
   */
  void setMinLevel(Level minLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  Level getMaxLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param maxLevel maxLevel.
   * @author admin
   */
  void setMaxLevel(Level maxLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @param args7       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @param args7       .
   * @param args8       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param datetime    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @param args7       .
   * @param args8       .
   * @param args9       .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8,
      StringBuilder args9);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @param args7    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @param args7    .
   * @param args8    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @param args7    .
   * @param args8    .
   * @param args9    .
   * @author admin
   */
  void log(
      String logLevel,
      String datetime,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8,
      StringBuilder args9);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @param args7       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @param args7       .
   * @param args8       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel    .
   * @param className   .
   * @param classMethod .
   * @param lineNumber  .
   * @param message     .
   * @param args1       .
   * @param args2       .
   * @param args3       .
   * @param args4       .
   * @param args5       .
   * @param args6       .
   * @param args7       .
   * @param args8       .
   * @param args9       .
   * @author admin
   */
  void log(
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8,
      StringBuilder args9);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @author admin
   */
  void log(
      String logLevel,
      String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @param args7    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @param args7    .
   * @param args8    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message  .
   * @param args1    .
   * @param args2    .
   * @param args3    .
   * @param args4    .
   * @param args5    .
   * @param args6    .
   * @param args7    .
   * @param args8    .
   * @param args9    .
   * @author admin
   */
  void log(
      String logLevel,
      String message,
      StringBuilder args1,
      StringBuilder args2,
      StringBuilder args3,
      StringBuilder args4,
      StringBuilder args5,
      StringBuilder args6,
      StringBuilder args7,
      StringBuilder args8,
      StringBuilder args9);
}
