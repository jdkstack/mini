package org.jdkstack.pool.core;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ThreadWorker extends AbstractQueuedSynchronizer implements Runnable {

  public ThreadWorker() {
    setState(-1);
  }

  @Override
  public void run() {
    //
  }
}
