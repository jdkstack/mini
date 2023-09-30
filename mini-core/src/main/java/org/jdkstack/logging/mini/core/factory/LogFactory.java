package org.jdkstack.logging.mini.core.factory;

import org.jdkstack.logging.mini.api.context.LogRecorderContextFactory;
import org.jdkstack.logging.mini.api.factory.Factory;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.context.AsyncLogRecorderContextFactory;

/**
 * LogFactory核心类.
 *
 * <p>.
 *
 * @author admin
 */
public final class LogFactory implements Factory {
  /** 静态的上下文对象工厂，创建一个上下文对象. */
  private static final LogRecorderContextFactory FACTORY = new AsyncLogRecorderContextFactory();

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
  public static Recorder getRecorder(final Class<?> clazz) {
    return FACTORY.getRecorder(clazz.getName());
  }
}
