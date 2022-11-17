package org.jdkstack.logging.mini.core;

import org.jdkstack.jdkbean.api.context.Context;
import org.jdkstack.jdkbean.core.annotation.ComponentScan;
import org.jdkstack.jdkbean.core.annotation.Filter;
import org.jdkstack.jdkbean.core.application.Application;
import org.jdkstack.jdkbean.core.context.ApplicationContext;

/**
 * 启动类,扫描要管理的Bean.
 *
 * <p>任何想要使用依赖注入的类都必须在注解ComponentScan中注册。
 *
 * @author admin
 */
@ComponentScan(
    value = {
        "org.jdkstack.logging.mini.core.resource.LevelFactory",
        "org.jdkstack.logging.mini.core.resource.LevelManager",
        "org.jdkstack.logging.mini.core.resource.FilterFactory",
        "org.jdkstack.logging.mini.core.resource.FilterManager",
        "org.jdkstack.logging.mini.core.resource.FormatterFactory",
        "org.jdkstack.logging.mini.core.resource.FormatterManager",
        "org.jdkstack.logging.mini.core.resource.HandlerFactory",
        "org.jdkstack.logging.mini.core.resource.RecorderFactory",
        "org.jdkstack.logging.mini.core.resource.HandlerManager",
        "org.jdkstack.logging.mini.core.resource.RecorderManager"},
    excludeFilters = {@Filter(String.class), @Filter(String.class)}
)
public final class StartApplication {

  /** 上下文环境. */
  private static final Context CONTEXT = new ApplicationContext();

  private StartApplication() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return context.
   * @author admin
   */
  public static Context context() {
    return CONTEXT;
  }

  static {
    Application.run(StartApplication.class, CONTEXT);
  }
}
