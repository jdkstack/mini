package org.jdkstack.asynchronous.core.context;

import java.util.concurrent.ExecutorService;
import org.jdkstack.asynchronous.api.context.WorkerContext;
import org.jdkstack.asynchronous.api.worker.Worker;
import org.jdkstack.asynchronous.core.TaskWorker;

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
    // 创建一个线程任务,包装事件处理对象.
    TaskWorker poll = new TaskWorker();
    poll.setEvent(event);
    poll.setHandler(handler);
    // 将任务提交到线程池中.
    this.executorService.execute(poll);
  }
}
