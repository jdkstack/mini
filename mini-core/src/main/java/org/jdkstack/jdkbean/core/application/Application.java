package org.jdkstack.jdkbean.core.application;

import org.jdkstack.jdkbean.api.context.Context;

/**
 * 启动类.
 *
 * <p>初始化.
 *
 * @author admin
 */
public class Application {

  private final Context context;

  public Application(final Context context) {
    this.context = context;
  }

  public final void run(final Class<?> application) {
    this.context.scan(application);
  }

  public static void run(final Class<?> application, final Context context) {
    new Application(context).run(application);
  }
}
