package org.jdkstack.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

import java.util.concurrent.TimeUnit;

public class ExecutorCompletionService<V> implements CompletionService<V> {
  private final Executor executor;
  private final AbstractExecutorService aes;
  private final BlockingQueue<Future<V>> completionQueue;

  /** FutureTask extension to enqueue upon completion. */
  private static class QueueingFuture<V> extends FutureTask<Void> {
    QueueingFuture(RunnableFuture<V> task, BlockingQueue<Future<V>> completionQueue) {
      super(task, null);
      this.task = task;
      this.completionQueue = completionQueue;
    }

    private final Future<V> task;
    private final BlockingQueue<Future<V>> completionQueue;

    protected void done() {
      completionQueue.add(task);
    }
  }

  private RunnableFuture<V> newTaskFor(Callable<V> task) {
    if (aes == null) return new FutureTask<V>(task);
    else return aes.newTaskFor(task);
  }

  private RunnableFuture<V> newTaskFor(Runnable task, V result) {
    if (aes == null) return new FutureTask<V>(task, result);
    else return aes.newTaskFor(task, result);
  }

  /**
   * Creates an ExecutorCompletionService using the supplied executor for base task execution and a
   * {@link LinkedBlockingQueue} as a completion queue.
   *
   * @param executor the executor to use
   * @throws NullPointerException if executor is {@code null}
   */
  public ExecutorCompletionService(Executor executor) {
    if (executor == null) throw new NullPointerException();
    this.executor = executor;
    this.aes =
        (executor instanceof AbstractExecutorService) ? (AbstractExecutorService) executor : null;
    this.completionQueue = new LinkedBlockingQueue<Future<V>>();
  }

  /**
   * Creates an ExecutorCompletionService using the supplied executor for base task execution and
   * the supplied queue as its completion queue.
   *
   * @param executor the executor to use
   * @param completionQueue the queue to use as the completion queue normally one dedicated for use
   *     by this service. This queue is treated as unbounded -- failed attempted {@code Queue.add}
   *     operations for completed tasks cause them not to be retrievable.
   * @throws NullPointerException if executor or completionQueue are {@code null}
   */
  public ExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> completionQueue) {
    if (executor == null || completionQueue == null) throw new NullPointerException();
    this.executor = executor;
    this.aes =
        (executor instanceof AbstractExecutorService) ? (AbstractExecutorService) executor : null;
    this.completionQueue = completionQueue;
  }

  /**
   * @throws RejectedExecutionException {@inheritDoc}
   * @throws NullPointerException {@inheritDoc}
   */
  public Future<V> submit(Callable<V> task) {
    if (task == null) throw new NullPointerException();
    RunnableFuture<V> f = newTaskFor(task);
    executor.execute(new ExecutorCompletionService.QueueingFuture<V>(f, completionQueue));
    return f;
  }

  /**
   * @throws RejectedExecutionException {@inheritDoc}
   * @throws NullPointerException {@inheritDoc}
   */
  public Future<V> submit(Runnable task, V result) {
    if (task == null) throw new NullPointerException();
    RunnableFuture<V> f = newTaskFor(task, result);
    executor.execute(
        new QueueingFuture<V>(f, completionQueue));
    return f;
  }

  public Future<V> take() throws InterruptedException {
    return completionQueue.take();
  }

  public Future<V> poll() {
    return completionQueue.poll();
  }

  public Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException {
    return completionQueue.poll(timeout, unit);
  }
}
