package org.jdkstack.pool.core;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.pool.core.policy.AbortPolicy;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

public class ThreadPoolExecutor extends AbstractExecutorService {
  // Integer.SIZE =32 ,用于表示二进制补码形式的int值的位数, Integer.SIZE - 3= 29.
  private static final int COUNT_BITS = Integer.SIZE - 3;
  // 10进制 536870911  二进制 0001-1111-1111-1111-1111-1111-1111-1111
  private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;
  // 用高位high-order bits存储runState运行状态.
  // -536870912  前三位000用来表示runState运行状态.
  private static final int RUNNING = -1 << COUNT_BITS;
  // 0
  private static final int SHUTDOWN = 0 << COUNT_BITS;
  // 536870912
  private static final int STOP = 1 << COUNT_BITS;
  // 1073741824
  private static final int TIDYING = 2 << COUNT_BITS;
  // 1610612736
  private static final int TERMINATED = 3 << COUNT_BITS;
  // -536870912
  private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
  // 线程存活时间.
  private volatile long keepAliveTime = 0L;

  private volatile int corePoolSize = 2;

  private volatile int maximumPoolSize = 3;
  //
  private volatile boolean allowCoreThreadTimeOut;

  private volatile RejectedExecutionHandler handler;

  /** 有界数组阻塞队列,存储线程任务. */
  private final MpmcBlockingQueueV3<Runnable> workQueue =
      new MpmcBlockingQueueV3<>(1024, TaskWorker::new);

  private final ReentrantLock mainLock = new ReentrantLock();

  private final Condition termination = mainLock.newCondition();

  // 线程池中所有线程.
  private final HashSet<Runnable> workers = new HashSet<>();

  private int largestPoolSize;

  private long completedTaskCount;

  private volatile ThreadFactory threadFactory = new LogThreadFactory("thread-pool", null);

  private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();

  private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");

  public ThreadPoolExecutor(
      int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue) {
    this.init();
    this.init();
  }

  // 获取线程池的状态.
  private static int runStateOf(int c) {
    return c & ~COUNT_MASK;
  }
  // 获取线程的个数.
  private static int workerCountOf(int c) {
    return c & COUNT_MASK;
  }

  private static int ctlOf(int rs, int wc) {
    return rs | wc;
  }

  private static boolean runStateLessThan(int c, int s) {
    return c < s;
  }

  private static boolean runStateAtLeast(int c, int s) {
    return c >= s;
  }

  private static boolean isRunning(int c) {
    return c < SHUTDOWN;
  }

  // 添加任务时.
  private boolean compareAndIncrementWorkerCount(int expect) {
    return ctl.compareAndSet(expect, expect + 1);
  }

  // 获取任务时.
  private boolean compareAndDecrementWorkerCount(int expect) {
    return ctl.compareAndSet(expect, expect - 1);
  }

  private void decrementWorkerCount() {
    ctl.addAndGet(-1);
  }

  private void advanceRunState(int targetState) {
    // assert targetState == SHUTDOWN || targetState == STOP;
    for (; ; ) {
      int c = ctl.get();
      if (runStateAtLeast(c, targetState)
          || ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c)))) break;
    }
  }

  final void tryTerminate() {
    for (; ; ) {
      int c = ctl.get();
      if (isRunning(c)
          || runStateAtLeast(c, TIDYING)
          || (runStateLessThan(c, STOP) && !workQueue.isEmpty())) return;
      if (workerCountOf(c) != 0) { // Eligible to terminate
        interruptIdleWorkers(ONLY_ONE);
        return;
      }

      final ReentrantLock mainLock = this.mainLock;
      mainLock.lock();
      try {
        if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
          try {
            terminated();
          } finally {
            ctl.set(ctlOf(TERMINATED, 0));
            termination.signalAll();
          }
          return;
        }
      } finally {
        mainLock.unlock();
      }
      // else retry on failed CAS
    }
  }

  private void checkShutdownAccess() {
    // assert mainLock.isHeldByCurrentThread();
    SecurityManager security = System.getSecurityManager();
    if (security != null) {
      security.checkPermission(shutdownPerm);
      /*  for (Runnable w : workers) security.checkAccess(w.thread);*/
    }
  }

  private void interruptWorkers() {
    // assert mainLock.isHeldByCurrentThread();
    /*  for (Runnable w : workers) w.interruptIfStarted();*/
  }

  private void interruptIdleWorkers(boolean onlyOne) {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    /*  try {
      for (Worker w : workers) {
        Thread t = w.thread;
        if (!t.isInterrupted() && w.tryLock()) {
          try {
            t.interrupt();
          } catch (SecurityException ignore) {
          } finally {
            w.unlock();
          }
        }
        if (onlyOne) break;
      }
    } finally {
      mainLock.unlock();
    }*/
  }

  private void interruptIdleWorkers() {
    interruptIdleWorkers(false);
  }

  private static final boolean ONLY_ONE = true;

  final void reject(Runnable command) {
    handler.rejectedExecution(command, this);
  }

  void onShutdown() {}

  /* private List<Runnable> drainQueue() {
    BlockingQueue<Runnable> q = workQueue;
    ArrayList<Runnable> taskList = new ArrayList<>();
    q.drainTo(taskList);
    if (!q.isEmpty()) {
      for (Runnable r : q.toArray(new Runnable[0])) {
        if (q.remove(r)) taskList.add(r);
      }
    }
    return taskList;
  }*/

  private boolean addWorker(boolean core) {
    retry:
    for (int c = ctl.get(); ; ) {
      // Check if queue empty only if necessary.
      if (runStateAtLeast(c, SHUTDOWN) && (runStateAtLeast(c, STOP) || workQueue.isEmpty()))
        return false;

      for (; ; ) {
        if (workerCountOf(c) >= ((core ? corePoolSize : maximumPoolSize) & COUNT_MASK))
          return false;
        if (compareAndIncrementWorkerCount(c)) break retry;
        c = ctl.get(); // Re-read ctl
        if (runStateAtLeast(c, SHUTDOWN)) continue retry;
        // else CAS failed due to workerCount change; retry inner loop
      }
    }

    boolean workerStarted = false;
    boolean workerAdded = false;
    // 创建任务.
    final Runnable threadWorker = new ThreadWorker();
    // 创建线程.
    final Thread thread = this.threadFactory.newThread(threadWorker);
    try {
      if (thread != null) {
        // final ReentrantLock mainLock = this.mainLock;
        // mainLock.lock();
        try {
          // Recheck while holding lock.
          // Back out on ThreadFactory failure or if
          // shut down before lock acquired.
          int c = ctl.get();

          if (isRunning(c) || (runStateLessThan(c, STOP))) {
            if (thread.isAlive()) // precheck that t is startable
            throw new IllegalThreadStateException();
            workers.add(threadWorker);
            int s = workers.size();
            if (s > largestPoolSize) largestPoolSize = s;
            workerAdded = true;
          }
        } finally {
          // mainLock.unlock();
        }
        if (workerAdded) {
          // 启动线程.
          thread.start();
          workerStarted = true;
        }
      }
    } finally {
      if (!workerStarted) addWorkerFailed(threadWorker);
    }
    return workerStarted;
  }

  private void addWorkerFailed(Runnable w) {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      if (w != null) workers.remove(w);
      decrementWorkerCount();
      tryTerminate();
    } finally {
      mainLock.unlock();
    }
  }

  private void processWorkerExit(Runnable w, boolean completedAbruptly) {
    if (completedAbruptly) // If abrupt, then workerCount wasn't adjusted
    decrementWorkerCount();

    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      // completedTaskCount += w.completedTasks;
      workers.remove(w);
    } finally {
      mainLock.unlock();
    }

    tryTerminate();

    int c = ctl.get();
    if (runStateLessThan(c, STOP)) {
      if (!completedAbruptly) {
        int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
        if (min == 0 && !workQueue.isEmpty()) min = 1;
        if (workerCountOf(c) >= min) return; // replacement not needed
      }
      addWorker(false);
    }
  }

  private Runnable getTask() {
    boolean timedOut = false; // Did the last poll() time out?

    for (; ; ) {
      int c = ctl.get();

      // Check if queue empty only if necessary.
      if (runStateAtLeast(c, SHUTDOWN) && (runStateAtLeast(c, STOP) || workQueue.isEmpty())) {
        decrementWorkerCount();
        return null;
      }

      int wc = workerCountOf(c);

      // Are workers subject to culling?
      boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

      if ((wc > maximumPoolSize || (timed && timedOut)) && (wc > 1 || workQueue.isEmpty())) {
        if (compareAndDecrementWorkerCount(c)) return null;
        continue;
      }

      try {
        // timed ? workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
        Runnable r = workQueue.head();
        System.out.println(Thread.currentThread().getName() + "获取了任务" + r);
        if (r != null) return r;
        timedOut = true;
      } catch (Exception retry) { // Interrupted
        timedOut = false;
      }
    }
  }

  final void runWorker() {
    Thread wt = Thread.currentThread();
    // Runnable task = w.firstTask;
    // w.firstTask = null;
    // w.unlock(); // allow interrupts
    boolean completedAbruptly = true;
    Runnable task = null;
    try {
      while (task != null || (task = getTask()) != null) {
        // w.lock();
        // If pool is stopping, ensure thread is interrupted;
        // if not, ensure thread is not interrupted.  This
        // requires a recheck in second case to deal with
        // shutdownNow race while clearing interrupt
        if ((runStateAtLeast(ctl.get(), STOP)
                || (Thread.interrupted() && runStateAtLeast(ctl.get(), STOP)))
            && !wt.isInterrupted()) wt.interrupt();
        try {
          // beforeExecute(wt, task);
          try {
            task.run();
            // afterExecute(task, null);
          } catch (Throwable ex) {
            // afterExecute(task, ex);
            throw ex;
          }
        } finally {
          workQueue.end();
          task = null;
          // w.completedTasks++;
          // w.unlock();
        }
      }
      completedAbruptly = false;
    } finally {
      // processWorkerExit(task, completedAbruptly);
    }
  }

  public void init() {
    /*
     * 分三步:
     * 1. 核心线程没满, 创建一个新线程, CAS检查runState和workerCount.
     * 2. 核心线程满了, 将线程任务加入队列. 继续CAS检查runState和workerCount, 防止线程死亡或者pool shut down.
     * 3. If we cannot queue task, then we try to add a new
     * thread.  If it fails, we know we are shut down or saturated
     * and so reject the task.
     */
    int c = ctl.get();
    // 第一步.
    if (workerCountOf(c) < corePoolSize) {
      if (addWorker(true)) {
        return;
      }
      c = ctl.get();
    }
    // 第二步.
    if (isRunning(c)) {
      int recheck = ctl.get();
      // 重新检查状态, 创建一个新线程.
      if (workerCountOf(recheck) == 0) {
        addWorker(false);
      }
    }
  }

  public void shutdown() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      checkShutdownAccess();
      advanceRunState(SHUTDOWN);
      interruptIdleWorkers();
      onShutdown(); // hook for ScheduledThreadPoolExecutor
    } finally {
      mainLock.unlock();
    }
    tryTerminate();
  }

  public List<Runnable> shutdownNow() {
    List<Runnable> tasks = null;
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      checkShutdownAccess();
      advanceRunState(STOP);
      interruptWorkers();
      // tasks = drainQueue();
    } finally {
      mainLock.unlock();
    }
    tryTerminate();
    return tasks;
  }

  public boolean isShutdown() {
    return runStateAtLeast(ctl.get(), SHUTDOWN);
  }

  boolean isStopped() {
    return runStateAtLeast(ctl.get(), STOP);
  }

  public boolean isTerminating() {
    int c = ctl.get();
    return runStateAtLeast(c, SHUTDOWN) && runStateLessThan(c, TERMINATED);
  }

  public boolean isTerminated() {
    return runStateAtLeast(ctl.get(), TERMINATED);
  }

  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    long nanos = unit.toNanos(timeout);
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      while (runStateLessThan(ctl.get(), TERMINATED)) {
        if (nanos <= 0L) return false;
        nanos = termination.awaitNanos(nanos);
      }
      return true;
    } finally {
      mainLock.unlock();
    }
  }

  @Deprecated(since = "9")
  protected void finalize() {}

  public void setThreadFactory(ThreadFactory threadFactory) {
    if (threadFactory == null) throw new NullPointerException();
    this.threadFactory = threadFactory;
  }

  public ThreadFactory getThreadFactory() {
    return threadFactory;
  }

  public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
    if (handler == null) throw new NullPointerException();
    this.handler = handler;
  }

  public RejectedExecutionHandler getRejectedExecutionHandler() {
    return handler;
  }

  public void setCorePoolSize(int corePoolSize) {
    if (corePoolSize < 0 || maximumPoolSize < corePoolSize) throw new IllegalArgumentException();
    int delta = corePoolSize - this.corePoolSize;
    this.corePoolSize = corePoolSize;
    if (workerCountOf(ctl.get()) > corePoolSize) interruptIdleWorkers();
    else if (delta > 0) {
      // We don't really know how many new threads are "needed".
      // As a heuristic, prestart enough new workers (up to new
      // core size) to handle the current number of tasks in
      // queue, but stop if queue becomes empty while doing so.
      int k = Math.min(delta, workQueue.size());
      while (k-- > 0 && addWorker(true)) {
        if (workQueue.isEmpty()) break;
      }
    }
  }

  public int getCorePoolSize() {
    return corePoolSize;
  }

  public boolean prestartCoreThread() {
    return workerCountOf(ctl.get()) < corePoolSize && addWorker(true);
  }

  void ensurePrestart() {
    int wc = workerCountOf(ctl.get());
    if (wc < corePoolSize) addWorker(true);
    else if (wc == 0) addWorker(false);
  }

  public int prestartAllCoreThreads() {
    int n = 0;
    while (addWorker(true)) ++n;
    return n;
  }

  public boolean allowsCoreThreadTimeOut() {
    return allowCoreThreadTimeOut;
  }

  public void allowCoreThreadTimeOut(boolean value) {
    if (value && keepAliveTime <= 0)
      throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
    if (value != allowCoreThreadTimeOut) {
      allowCoreThreadTimeOut = value;
      if (value) interruptIdleWorkers();
    }
  }

  public void setMaximumPoolSize(int maximumPoolSize) {
    if (maximumPoolSize <= 0 || maximumPoolSize < corePoolSize)
      throw new IllegalArgumentException();
    this.maximumPoolSize = maximumPoolSize;
    if (workerCountOf(ctl.get()) > maximumPoolSize) interruptIdleWorkers();
  }

  public int getMaximumPoolSize() {
    return maximumPoolSize;
  }

  public void setKeepAliveTime(long time, TimeUnit unit) {
    if (time < 0) throw new IllegalArgumentException();
    if (time == 0 && allowsCoreThreadTimeOut())
      throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
    long keepAliveTime = unit.toNanos(time);
    long delta = keepAliveTime - this.keepAliveTime;
    this.keepAliveTime = keepAliveTime;
    if (delta < 0) interruptIdleWorkers();
  }

  public long getKeepAliveTime(TimeUnit unit) {
    return unit.convert(keepAliveTime, TimeUnit.NANOSECONDS);
  }

  /*  public BlockingQueue<Runnable> getQueue() {
    return workQueue;
  }*/

  public boolean remove(Runnable task) {
    boolean removed = false; // workQueue.remove(task);
    tryTerminate(); // In case SHUTDOWN and now empty
    return removed;
  }

  /*public void purge() {
    final BlockingQueue<Runnable> q = workQueue;
    try {
      Iterator<Runnable> it = q.iterator();
      while (it.hasNext()) {
        Runnable r = it.next();
        if (r instanceof Future<?> && ((Future<?>) r).isCancelled()) it.remove();
      }
    } catch (ConcurrentModificationException fallThrough) {
      // Take slow path if we encounter interference during traversal.
      // Make copy for traversal and call remove for cancelled entries.
      // The slow path is more likely to be O(N*N).
      for (Object r : q.toArray())
        if (r instanceof Future<?> && ((Future<?>) r).isCancelled()) q.remove(r);
    }

    tryTerminate(); // In case SHUTDOWN and now empty
  }*/

  public int getPoolSize() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      // Remove rare and surprising possibility of
      // isTerminated() && getPoolSize() > 0
      return runStateAtLeast(ctl.get(), TIDYING) ? 0 : workers.size();
    } finally {
      mainLock.unlock();
    }
  }

  public int getActiveCount() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      int n = 0;
      /*  for (Runnable w : workers) if (w.isLocked()) ++n;*/
      return n;
    } finally {
      mainLock.unlock();
    }
  }

  public int getLargestPoolSize() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      return largestPoolSize;
    } finally {
      mainLock.unlock();
    }
  }

  public long getTaskCount() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      long n = completedTaskCount;
      /*      for (Runnable w : workers) {
        n += w.completedTasks;
        if (w.isLocked()) ++n;
      }*/
      return n + workQueue.size();
    } finally {
      mainLock.unlock();
    }
  }

  public long getCompletedTaskCount() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
      long n = completedTaskCount;
      /*   for (Runnable w : workers) n += w.completedTasks;*/
      return n;
    } finally {
      mainLock.unlock();
    }
  }

  protected void beforeExecute(Thread t, Runnable r) {}

  protected void afterExecute(Runnable r, Throwable t) {}

  protected void terminated() {}

  @Override
  public void execute(Runnable command) {}

  private final class ThreadWorker extends AbstractQueuedSynchronizer implements Runnable {

    private static final long serialVersionUID = 6138294804551838833L;

    // final Thread thread;
    // Runnable firstTask;
    volatile long completedTasks;

    ThreadWorker() {
      setState(-1); // inhibit interrupts until runWorker
      // this.firstTask = firstTask;
      // this.thread = getThreadFactory().newThread(this);
    }

    public void run() {
      runWorker();
    }

    protected boolean isHeldExclusively() {
      return getState() != 0;
    }

    protected boolean tryAcquire(int unused) {
      if (compareAndSetState(0, 1)) {
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      return false;
    }

    protected boolean tryRelease(int unused) {
      setExclusiveOwnerThread(null);
      setState(0);
      return true;
    }

    public void lock() {
      acquire(1);
    }

    public boolean tryLock() {
      return tryAcquire(1);
    }

    public void unlock() {
      release(1);
    }

    public boolean isLocked() {
      return isHeldExclusively();
    }

    /*    void interruptIfStarted() {
      Thread t;
      if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
        try {
          t.interrupt();
        } catch (SecurityException ignore) {
        }
      }
    }*/
  }

  public TaskWorker getTaskWorker() {
    TaskWorker tail = (TaskWorker) workQueue.tail();
    return tail;
  }

  public void start() {
    workQueue.start();
  }
}
