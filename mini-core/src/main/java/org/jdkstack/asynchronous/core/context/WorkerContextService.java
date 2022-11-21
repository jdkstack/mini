package org.jdkstack.asynchronous.core.context;

import java.util.concurrent.ExecutorService;
import org.jdkstack.asynchronous.api.context.WorkerContext;
import org.jdkstack.asynchronous.api.worker.Worker;
import org.jdkstack.asynchronous.core.TaskWorker;
import org.jdkstack.asynchronous.core.thread.CustomLogThread;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class WorkerContextService extends AbstractContext implements WorkerContext {

  /** 执行任务的线程池. */
  private final ExecutorService executorService;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param executorService 执行任务的线程池.
   * @author admin
   */
  public WorkerContextService(
      final ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * 提交线程任务到线程池.
   *
   * <p>提交线程任务到线程池.
   *
   * @param task 任务.
   * @author admin
   */
  @Override
  public final void executeInExecutorService(final Runnable task) {
    this.executorService.execute(task);
  }

  /**
   * 提交事件到事件处理对象.
   *
   * <p>提交事件到事件处理对象.
   *
   * @param event   事件.
   * @param handler 事件处理对象.
   * @author admin
   */
  @Override
  public final <T> void executeInExecutorService(final T event, final Worker<T> handler) {
    CustomLogThread thread = (CustomLogThread) Thread.currentThread();
    // 创建一个线程任务,包装事件处理对象.
    TaskWorker poll = thread.getTr();
    poll.setEvent(event);
    poll.setHandler(handler);
    // 将任务提交到线程池中.
    this.executorService.execute(poll);
  }

  @Override
  public final <T> void runAsContext(final T event, final Worker<T> handler) {
    handler.handle(event);
  }
}
