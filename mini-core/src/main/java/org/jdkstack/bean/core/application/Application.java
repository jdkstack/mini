package org.jdkstack.bean.core.application;

import org.jdkstack.bean.api.context.Context;

/**
 * 启动类.
 *
 * <p>初始化.
 *
 * @author admin
 */
public class Application {

  /** . */
  private final Context context;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param context .
   * @author admin
   */
  public Application(final Context context) {
    this.context = context;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param application .
   * @author admin
   */
  public final void run(final Class<?> application) {
    this.context.scan(application);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param application .
   * @param context .
   * @author admin
   */
  public static void run(final Class<?> application, final Context context) {
    new Application(context).run(application);
  }
}
