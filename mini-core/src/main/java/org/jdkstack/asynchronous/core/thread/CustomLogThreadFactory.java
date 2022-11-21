package org.jdkstack.asynchronous.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂,主要是用来创建线程,提供给系统底层.
 *
 * <p>线程工厂,主要是用来创建线程,提供给系统底层.
 *
 * @author admin
 */
public class CustomLogThreadFactory implements ThreadFactory {

  /**
   * 线程名前缀.
   */
  private final String prefix;
  /**
   * 线程名后缀计数.
   */
  private final AtomicInteger threadCount = new AtomicInteger(0);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefixParam 线程名前缀.
   * @author admin
   */
  public CustomLogThreadFactory(final String prefixParam) {
    // 线程名前缀.
    this.prefix = prefixParam;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param runnable 任务.
   * @return Thread 返回一个LogThread线程.
   * @author admin
   */
  @Override
  public final Thread newThread(final Runnable runnable) {
    // 创建线程,由线程工厂触发.
    final int andIncrement = this.threadCount.getAndIncrement();
    final Thread logThread = new CustomLogThread(runnable, this.prefix + andIncrement);
    // 注册线程.
    //this.checker.registerThread(logThread);
    // 设置线程为守护线程.不能阻止外部调用程序jvm退出.
    logThread.setDaemon(true);
    return logThread;
  }
}
