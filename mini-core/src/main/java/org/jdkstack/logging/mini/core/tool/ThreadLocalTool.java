package org.jdkstack.logging.mini.core.tool;

import org.jdkstack.logging.mini.core.thread.LogThread;

public class ThreadLocalTool {

  private static final ThreadLocal<LogThread> rtl = new ThreadLocal<>();

  public static LogThread getLogThread() {
    Thread thread = Thread.currentThread();
    LogThread logThread;
    if (thread instanceof LogThread) {
      logThread = (LogThread) Thread.currentThread();
    } else {
      logThread = rtl.get();
      if (logThread == null) {
        logThread = new LogThread(null, "log-c");
        rtl.set(logThread);
      }
    }
    return logThread;
  }
}
