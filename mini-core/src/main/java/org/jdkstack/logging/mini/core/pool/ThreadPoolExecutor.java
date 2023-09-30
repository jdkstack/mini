package org.jdkstack.logging.mini.core.pool;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.core.ringbuffer.MpmcBlockingQueueV3;

public class ThreadPoolExecutor extends AbstractExecutorService {
  /** 使用后8位用来表示核心线程数. */
  private static final int BIT = 8;
  /** 使用前8位能表示的最大核心线程数是255个. */
  private static final int COUNT = (1 << BIT) - 1;
  /** 线程池已经启动,线程等待执行任务(没有任务). */
  private static final int STARTTED = 1 << BIT;
  /** 线程池正在运行,线程正在执行任务(拿到了一个新任务). */
  private static final int RUNNING = 2 << BIT;
  /** 线程池正在停止,还有线程在执行任务,不接受新任务. */
  private static final int STOPPING = 3 << BIT;
  /** 线程池已经停止,所有线程执行完的状态. */
  private static final int STOPPED = 4 << BIT;
  /** 存储线程池状态和核心线程数量. */
  private final AtomicInteger core = new AtomicInteger(core(STARTTED, 0));
  /** 核心线程没有任务可以执行时,多少时间后退出. */
  private final long keepAliveTime;
  /** 最小核心线程数量. */
  private final int minSize;
  /** 最大核心线程数量. */
  private final int maxSize;
  /** 有界数组阻塞队列,存储线程任务. */
  private final MpmcBlockingQueueV3<Runnable> workQueue;
  /** 线程池中所有线程. */
  private final Collection<Runnable> workers = new HashSet<>(16);

  /** 线程池中线程工厂. */
  private final ThreadFactory threadFactory = new LogThreadFactory("thread-pool", null);

  public ThreadPoolExecutor(
          final int minSize,
          final int maxSize,
          final long keepAliveTime,
          final TimeUnit unit,
          final MpmcBlockingQueueV3<Runnable> queue) {
    this.workQueue = queue;
    this.minSize = minSize;
    this.maxSize = maxSize;
    this.keepAliveTime = keepAliveTime;
    // 创建核心线程.
    for (int i = 0; i < minSize; i++) {
      this.thread();
    }
  }

  // 与运算,获取线程池的状态.
  private static int state(final int core) {
    // 与运算,都是1时才为1,其他为0.
    return core & ~COUNT;
  }

  // 与运算,获取核心线程的数量.
  private static int count(final int core) {
    // 与运算,都是1时才为1,其他为0.
    return core & COUNT;
  }

  // 线程池状态|核心线程的数量进行或运算.
  private static int core(final int state, final int count) {
    // 使用二进制或运算,有一个是1则结果是1.
    return state | count;
  }

  private static boolean runStateLessThan(final int c, final int s) {
    return c < s;
  }

  private static boolean runStateAtLeast(final int c, final int s) {
    return c >= s;
  }

  // 添加任务时+1.
  private boolean compareAndIncrementWorkerCount(final int expect) {
    return this.core.compareAndSet(expect, expect + 1);
  }

  // 获取任务时-1.
  private boolean compareAndDecrementWorkerCount(final int expect) {
    return this.core.compareAndSet(expect, expect - 1);
  }

  // 这仅在线程突然终止时调用（请参阅processWorkerExit）。其他递减在getTask中执行。
  private void decrementWorkerCount() {
    this.core.addAndGet(-1);
  }

  // 转换状态->SHUTDOWN或者STOP
  private void advanceRunState(final int targetState) {
    for (; ; ) {
      final int c = this.core.get();
      if (runStateAtLeast(c, targetState) || this.core.compareAndSet(c, core(targetState, count(c)))) {
        break;
      }
    }
  }

  public final void thread() {
    // 创建任务.
    final Runnable threadWorker = new ThreadWorker();
    // 创建线程.
    final Thread thread = this.threadFactory.newThread(threadWorker);
    // 启动线程.
    thread.start();
    //
    this.workers.add(threadWorker);
  }

  public final Runnable getTaskWorker() {
    return this.workQueue.tail();
  }

  public final void start() {
    this.workQueue.start();
  }

  private final class ThreadWorker implements Runnable {

    @Override
    public void run() {
      for (; ; ) {
        try {
          final Runnable task = ThreadPoolExecutor.this.workQueue.head();
          task.run();
        } finally {
          ThreadPoolExecutor.this.workQueue.end();
        }
      }
    }
  }
}
