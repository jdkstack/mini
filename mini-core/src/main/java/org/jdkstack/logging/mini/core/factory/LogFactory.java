package org.jdkstack.logging.mini.core.factory;

import org.jdkstack.bean.api.bean.Bean;
import org.jdkstack.logging.mini.api.factory.Factory;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.ReFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * LogFactory核心类，提供两个静态方法.
 *
 * <p>。
 *
 * @author admin
 */
public final class LogFactory implements Factory {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private LogFactory() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param clazz 使用类的简单名称作为日志的标志.
   * @return 返回一个Log对象.
   * @author admin
   */
  public static Recorder getLog(final Class<?> clazz) {
    try {
      // 全限定名.
      final String name = clazz.getName();
      return getLog(name);
    } catch (final Exception e) {
      throw new LogRuntimeException("创建对象时异常", e);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 使用简单名称作为日志的标志.
   * @return 返回一个Log对象.
   * @author admin
   */
  public static Recorder getLog(final String name) {
    final Bean logInfos = StartApplication.context().getBean("recorderFactory");
    final Object obj3 = logInfos.getObj();
    final ReFactory info = (ReFactory) obj3;
    return info.getRecorder(name);
  }
}
