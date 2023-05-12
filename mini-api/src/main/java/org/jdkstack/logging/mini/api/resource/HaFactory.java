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
   * @param key key.
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
}
