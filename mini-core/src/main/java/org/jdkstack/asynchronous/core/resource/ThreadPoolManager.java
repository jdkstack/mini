package org.jdkstack.asynchronous.core.resource;

import org.jdkstack.jdkbean.core.annotation.Component;
import org.jdkstack.jdkbean.core.annotation.ConstructorResource;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class ThreadPoolManager {

  /** . */
  private final ThreadPoolFactory threadPoolFactory;

  /**
   * .
   *
   * <p>.
   *
   * @param threadPoolFactory .
   * @author admin
   */
  @ConstructorResource("threadPoolFactory")
  public ThreadPoolManager(final ThreadPoolFactory threadPoolFactory) {
    this.threadPoolFactory = threadPoolFactory;
    this.init();
  }

  private void init() {
//
  }
}
