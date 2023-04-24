package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.resource.HaFactory;
import org.jdkstack.logging.mini.core.Internal;
import org.jdkstack.logging.mini.core.option.LogHandlerOption;

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
    // 初始化default Handler.
    this.init();
  }

  private void init() {
    // 指定默认位置。
    final HandlerOption handlerOption = new LogHandlerOption();
    this.create(handlerOption);
  }

  /**
   * .
   *
   * <p>.
   *
   * @param handlerOption .
   * @author admin
   */
  public final void create(final HandlerOption handlerOption) {
    try {
      // 获取当前类加载器.
      final ClassLoader systemClassLoader = Thread.currentThread().getContextClassLoader();
      // 使用当前的类加载器生成类.
      final Class<?> classObj = systemClassLoader.loadClass(handlerOption.getClassName());
      final Object handler =
          classObj.getConstructor(HandlerOption.class).newInstance(handlerOption);
      // 将处理器添加到系统类加载内.
      this.logHs.putIfAbsent(handlerOption.getName(), (Handler) handler);
    } catch (final Exception e) {
      Internal.log(e);
    }
  }
}
