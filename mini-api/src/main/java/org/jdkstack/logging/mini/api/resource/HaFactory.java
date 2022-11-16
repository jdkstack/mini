package org.jdkstack.logging.mini.api.resource;

import java.util.Map;
import org.jdkstack.logging.mini.api.handler.Handler;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public interface HaFactory {

  /**
   * .
   *
   * <p>.
   *
   * @param key   key.
   * @param value value.
   * @author admin
   */
  void putIfAbsent(String key, Handler value);

  /**
   * .
   *
   * <p>.
   *
   * @param handlerName handlerName.
   * @return Handler .
   * @author admin
   */
  Handler getHandler(String handlerName);

  /**
   * .
   *
   * <p>.
   *
   * @return Map.
   * @author admin
   */
  Map<String, Handler> getHandlers();

  /**
   * .
   *
   * <p>.
   *
   * @param name        name.
   * @param logLevel    logLevel.
   * @param message     message.
   * @param className   className.
   * @param classMethod classMethod.
   * @param lineNumber  lineNumber.
   * @author admin
   */
  void execute(
      String name,
      String logLevel,
      String className,
      String classMethod,
      int lineNumber,
      StringBuilder message);

  /**
   * .
   *
   * <p>.
   *
   * @param name        name.
   * @param datetime    datetime.
   * @param logLevel    logLevel.
   * @param message     message.
   * @param className   className.
   * @param classMethod classMethod.
   * @param lineNumber  lineNumber.
   * @author admin
   */
  void execute(
      String name,
      String logLevel,
      String datetime,
      String className,
      String classMethod,
      int lineNumber,
      StringBuilder message);
}
