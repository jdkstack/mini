package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
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
public class HandlerManager {

  /** . */
  private final HaFactory logHs;

  /**
   * .
   *
   * <p>.
   *
   * @param handlerFactory .
   * @author admin
   */
  @ConstructorResource("handlerFactory")
  public HandlerManager(final HaFactory handlerFactory) {
    this.logHs = handlerFactory;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param handler .
   * @author admin
   */
  public final void create(final String key, final Handler handler) {
    this.logHs.putIfAbsent(key, handler);
  }
}
