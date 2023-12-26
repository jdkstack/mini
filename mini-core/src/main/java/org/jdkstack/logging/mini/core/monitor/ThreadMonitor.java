package org.jdkstack.logging.mini.core.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.logging.mini.api.monitor.Monitor;

/**
 * 定时检查线程的运行时间.
 *
 * <p>当线程的run方法运行时间不超过组大阻塞时间blockTime,但是超过了线程运行的最大时间.
 *
 * <p>打印危险的消息,如果超过最大阻塞时间,打算线程堆栈信息,看看线程的run中运行的代码是什么.
 *
 * @author admin
 */
public class ThreadMonitor implements Monitor {

  /** 保存所有的线程, key是线程名字,value是线程. */
  public final Map<String, Thread> threads = new ConcurrentHashMap<>(32);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ThreadMonitor() {
    //
  }

  /**
   * 添加要监控的线程,这个线程必须是LogThread.
   *
   * <p>线程必须继承Thread.
   *
   * @param thread 要监控的线程.
   * @author admin
   */
  @Override
  public final void registerThread(final Thread thread) {
    // 添加要监控的线程,这个线程必须是LogThread.
    final String name = thread.getName();
    this.threads.put(name, thread);
  }


  /**
   * 添加要监控的线程,这个线程必须是LogThread.
   *
   * <p>线程必须继承Thread.
   *
   * @param name 要监控的线程.
   * @author admin
   */
  @Override
  public final boolean isNull(String name) {
    if (this.threads.get(name) == null) {
      return true;
    }
    return false;
  }
}
