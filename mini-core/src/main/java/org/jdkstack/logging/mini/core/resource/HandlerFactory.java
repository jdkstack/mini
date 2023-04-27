package org.jdkstack.logging.mini.core.resource;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.resource.HaFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class HandlerFactory implements HaFactory {

  /** . */
  private final Map<String, Handler> handlers = new ConcurrentHashMap<>(32);

  @Override
  public final void putIfAbsent(final String key, final Handler value) {
    this.handlers.put(key, value);
  }

  @Override
  public final Handler getHandler(final String handlerName) {
    return this.handlers.get(handlerName);
  }

  @Override
  public final Map<String, Handler> getHandlers() {
    return Collections.unmodifiableMap(this.handlers);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @param logLevel .
   * @param className .
   * @param classMethod .
   * @param lineNumber .
   * @param message .
   * @author admin
   */
  @Override
  public final void execute(
      final String name,
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    final Handler handler = this.handlers.get(name);
    handler.execute(logLevel, className, classMethod, lineNumber, message);
  }

  @Override
  public final void execute(
      final String name,
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    final Handler handler = this.handlers.get(name);
    handler.execute(logLevel, datetime, className, classMethod, lineNumber, message);
  }
}
