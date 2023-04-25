package org.jdkstack.logging.mini.core.factory;

import org.jdkstack.logging.mini.api.factory.Factory;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.ReFactory;
import org.jdkstack.logging.mini.core.StartApplication;

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
    // 全限定名.
    final String name = clazz.getName();
    final ReFactory info = StartApplication.getBean("recorderFactory", ReFactory.class);
    return info.getRecorder(name);
  }
}
