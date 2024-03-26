package org.jdkstack.logging.mini.core.tool;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.thread.LogProduceThread;

public class ThreadLocalTool {

  private static final Map<String, LogConsumeThread> logConsumeThreads = new HashMap<>();
  private static final Map<String, LogProduceThread> logProduceThreads = new HashMap<>();

  public static LogConsumeThread getLogConsumeThread() {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = thread.getName();
    LogConsumeThread logConsumeThread;
    if (thread instanceof LogConsumeThread) {
      logConsumeThread = (LogConsumeThread) Thread.currentThread();
    } else {
      logConsumeThread = logConsumeThreads.get(name);
      if (logConsumeThread == null) {
        logConsumeThread = new LogConsumeThread(null, "log-c");
        logConsumeThreads.put(name, logConsumeThread);
      }
    }
    return logConsumeThread;
  }

  public static LogProduceThread getLogProduceThread() {
    Thread thread = Thread.currentThread();
    long id = thread.getId();
    String name = thread.getName();
    LogProduceThread logProduceThread;
    if (thread instanceof LogProduceThread) {
      logProduceThread = (LogProduceThread) Thread.currentThread();
    } else {
      logProduceThread = logProduceThreads.get(name);
      if (logProduceThread == null) {
        logProduceThread = new LogProduceThread(null, "log-p");
        logProduceThreads.put(name, logProduceThread);
      }
    }
    return logProduceThread;
  }
}
