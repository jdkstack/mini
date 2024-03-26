package org.jdkstack.logging.mini.core.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.record.LogRecord;

/**
 * 自定义线程,便于系统内线程的监控.
 *
 * <p>比如设置自定义的线程名,线程计数等.
 *
 * @author admin
 */
public final class LogProduceThread extends Thread {

  /** 最大9个参数，容量16足够. */
  private static final int CAPACITY = 16;
  private static final int MASK = CAPACITY - 1;
  private final StringBuilder[] ringBuffer = new StringBuilder[CAPACITY];
  private int current;

  /** 全局Recorder日志计数. */
  private final AtomicLong globalRecorderCounter = new AtomicLong(0L);
  /** 所有方法计数. */
  private final AtomicLong totalMethodCounter = new AtomicLong(0L);
  /** 单个方法计数. */
  private final AtomicLong methodCounter = new AtomicLong(0L);
  /** 单个方法内每个日志级别分别计数. */
  private final Map<String, AtomicLong> maps = new HashMap<>(16);
  /** 当使用同步方式时，共享一个Record对象. */
  private final Record record = new LogRecord();

  /**
   * 线程开始运行的时间(毫秒).
   */
  private long execStart;

  /**
   * 自定义线程.
   *
   * <p>参数需要加final修饰.
   *
   * @param targetParam 线程任务.
   * @param nameParam   线程名.
   * @author admin
   */
  public LogProduceThread(final Runnable targetParam, final String nameParam) {
    super(targetParam, nameParam);
    for (int i = 0; i < ringBuffer.length; i++) {
      ringBuffer[i] = new StringBuilder(21);
    }
  }

  /**
   * 获取线程运行的开始时间.
   *
   * @return 返回线程的开始运行时间.
   * @author admin
   */
  public long startTime() {
    return this.execStart;
  }

  /**
   * 当线程开始时,开始时间设置成当前系统的时间戳毫秒数.
   *
   * @author admin
   */
  private void executeStart() {
    // 设置当前系统时间为开始时间,代表线程开始执行.
    this.execStart = System.currentTimeMillis();
  }

  private void executeEnd() {
    // 设置当前系统时间为0,代表线程执行完毕.
    this.execStart = 0;
  }

  /**
   * 给线程设置一个上下文环境对象.
   *
   * <p>代表线程正在运行着.
   *
   * @author admin
   */
  public void beginEmission() {
    // 设置执行开始时间.
    this.executeStart();
  }

  /**
   * 将线程上下文环境对象设置为空.
   *
   * <p>代表线程运行完毕.
   *
   * @author admin
   */
  public void endEmission() {
    // 设置执行结束时间.
    this.executeEnd();
  }

  public StringBuilder poll() {
    final StringBuilder result = ringBuffer[MASK & current++];
    result.setLength(0);
    return result;
  }

  public Record getRecord() {
    return record;
  }
}
