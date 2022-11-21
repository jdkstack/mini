package org.jdkstack.asynchronous.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程池任务队列背压策略.
 *
 * <p>当线程池队列满时,重新放入队列中.
 *
 * @author admin
 */
public class LogRejectedPolicy implements RejectedExecutionHandler {

  /**
   * 当线程池队列满时,使用的拒绝策略.
   *
   * <p>不会丢掉任务,而是阻塞,直到任务重新放入队列中为止.
   *
   * @param r .
   * @param e .
   * @author admin
   */
  @Override
  public final void rejectedExecution(final Runnable r, final ThreadPoolExecutor e) {
    // 如果线程池关闭了,直接返回.
    if (!e.isShutdown()) {
      // 不在主线执行任务,但是可以在主线程上阻塞,将任务重新加入到队列.
      final BlockingQueue<Runnable> queue = e.getQueue();
      // 循环执行,直到返回true为止.
      while (!queue.offer(r) && !Thread.currentThread().isInterrupted()) {
        // 休眠5ns.
        LockSupport.parkNanos(5L);
      }
    }
  }
}
