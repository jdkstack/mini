package org.jdkstack.asynchronous.core.context;

import org.jdkstack.asynchronous.api.context.Context;
import org.jdkstack.asynchronous.api.thread.LogThread;
import org.jdkstack.asynchronous.api.worker.Worker;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;

/**
 * 一个抽象的上下文对象,主要提供一些方法,用于监控线程的运行情况.
 *
 * <p>一般来说,上下文对象主要保存一些系统信息.
 *
 * @author admin
 */
public abstract class AbstractContext implements Context {

  /**
   * 用于在执行方法前后增加开始时间和结束时间.
   *
   * <p>用于监控方法的执行时间.
   *
   * @param event   作为handler的参数传递.
   * @param handler 一个处理器,用于执行具体业务逻辑.
   * @author admin
   */
  @Override
  public final <T> void dispatch(final T event, final Worker<T> handler) {
    try {
      // 增加开始时间.
      this.beginDispatch();
      // 执行具体业务,传递event参数.
      handler.handle(event);
    } catch (final Exception e) {
      throw new LogRuntimeException("dispatch异常", e);
    } finally {
      // 增加结束时间.
      this.endDispatch();
    }
  }

  /**
   * 首先判断,当前线程是不是LogThread.
   *
   * <p>不处理main线程.
   *
   * @author admin
   */
  @Override
  public final void beginDispatch() {
    // 获取当前线程.
    final Thread thread = Thread.currentThread();
    // 当前线程是不是LogThread.
    if (thread instanceof LogThread) {
      // 得到当前真实线程.
      final LogThread th = (LogThread) thread;
      // 设置开始时间.
      th.beginEmission();
    }
  }

  /**
   * 首先判断,当前线程是不是LogThread.
   *
   * <p>不处理main线程.
   *
   * @author admin
   */
  @Override
  public final void endDispatch() {
    // 获取当前线程.
    final Thread thread = Thread.currentThread();
    // 当前线程是不是LogThread.
    if (thread instanceof LogThread) {
      // 得到当前真实线程.
      final LogThread th = (LogThread) thread;
      // 设置结束时间.
      th.endEmission();
    }
  }
}
