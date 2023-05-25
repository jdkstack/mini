package org.jdkstack.pool.core;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class TaskWorker implements Runnable {

  private String test;

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + "-" + getTest());
    try {
      Thread.sleep(150);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public String getTest() {
    return this.test;
  }

  public void setTest(final String test) {
    this.test = test;
  }
}
