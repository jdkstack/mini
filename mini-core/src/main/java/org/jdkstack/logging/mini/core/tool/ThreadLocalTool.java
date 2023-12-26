package org.jdkstack.logging.mini.core.tool;

import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.thread.LogProduceThread;

public class ThreadLocalTool {

  private static final ThreadLocal<LogConsumeThread> lct = new ThreadLocal<>();
  private static final ThreadLocal<LogProduceThread> lpt = new ThreadLocal<>();

  public static LogConsumeThread getLogConsumeThread() {
    Thread thread = Thread.currentThread();
    LogConsumeThread logConsumeThread;
    if (thread instanceof LogConsumeThread) {
      logConsumeThread = (LogConsumeThread) Thread.currentThread();
    } else {
      logConsumeThread = lct.get();
      if (logConsumeThread == null) {
        logConsumeThread = new LogConsumeThread(null, "log-c");
        lct.set(logConsumeThread);
      }
    }
    return logConsumeThread;
  }

  public static LogProduceThread getLogProduceThread() {
    Thread thread = Thread.currentThread();
    LogProduceThread logProduceThread;
    if (thread instanceof LogProduceThread) {
      logProduceThread = (LogProduceThread) Thread.currentThread();
    } else {
      logProduceThread = lpt.get();
      if (logProduceThread == null) {
        logProduceThread = new LogProduceThread(null, "log-p");
        lpt.set(logProduceThread);
      }
    }
    return logProduceThread;
  }
}
