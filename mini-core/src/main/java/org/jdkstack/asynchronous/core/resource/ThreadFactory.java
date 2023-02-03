package org.jdkstack.asynchronous.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.asynchronous.api.context.WorkerContext;
import org.jdkstack.asynchronous.api.thread.LogThread;
import org.jdkstack.asynchronous.core.Constants;
import org.jdkstack.bean.core.annotation.Component;

/**
 * 定时检查线程的运行时间.
 *
 * <p>当线程的run方法运行时间不超过组大阻塞时间blockTime,但是超过了线程运行的最大时间.
 *
 * <p>打印危险的消息,如果超过最大阻塞时间,打算线程堆栈信息,看看线程的run中运行的代码是什么.
 *
 * @author admin
 */
@Component
public class ThreadFactory {

  /** 保存所有的线程,key是线程名字,value是线程. */
  private final Map<String, Thread> threads = new ConcurrentHashMap<>(32);


  /**
   * 添加要监控的线程,这个线程必须是LogThread.
   *
   * <p>线程必须继承Thread.
   *
   * @param thread 要监控的线程.
   * @author admin
   */
  public final void registerThread(final Thread thread) {
    // 添加要监控的线程,这个线程必须是LogThread.
    final String name = thread.getName();
    this.threads.put(name, thread);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param context c.
   * @author admin
   */
  public final void execute(final WorkerContext context) {
    // 原打算使用异步的方式,但是感觉不太合理.
    for (final Map.Entry<String, Thread> entry : this.threads.entrySet()) {
      // 要检查的线程(注册线程时必须是LogThread).
      final LogThread logThread = (LogThread) entry.getValue();
      // 当前系统时间毫秒数.
      final long currentTimeMillis = System.currentTimeMillis();
      // 线程开始执行时间的毫秒数.
      final long execStart = logThread.startTime();
      // 线程执行的时间.
      final long duration = currentTimeMillis - execStart;
      // 线程开始时间为0,表示线程还没运行(或者执行完毕).
      final boolean isExecStart = 0 == execStart;
      // 如果线程执行时间<低水位10秒,直接返回.
      final boolean isMaxExecTime = Constants.LOW > duration;
      if (isExecStart || isMaxExecTime) {
        return;
      }
      // 如果低水位(10秒)<线程执行时间<=高水位(15秒),打印详细日志说明.
      if (Constants.HIGH >= duration) {
        // 如果小于等于阻塞时间,打印线程异常warn信息.
        //LOG.info(name + "execute1 线程{}", event);
        //LOG.info(name + "execute2 锁定{}毫秒", duration);
        //LOG.info(name + "execute3 限制{}毫秒", Constants.HIGH);
      } else {
        // 如果大于高水位15秒,打印线程可能的异常信息.
        final StackTraceElement[] stackTraces = logThread.getStackTrace();
        for (final StackTraceElement stackTrace : stackTraces) {
          //LOG.info(name + "execute4 线程运行异常?堆栈信息:{}", stackTrace);
        }
      }
    }
  }
}
