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
public class ThreadManager {

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
  public ThreadManager(final ThreadPoolFactory threadPoolFactory) {
    this.threadPoolFactory = threadPoolFactory;
    this.init();
  }

  private void init() {
//
  }
}
