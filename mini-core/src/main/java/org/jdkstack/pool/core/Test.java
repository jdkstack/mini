package org.jdkstack.pool.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {

  public static void main(String[] args) {
    ThreadPoolExecutor logConsumer =
        new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
    /*    logConsumer.submit(
        () -> {
          try {
            Thread.sleep(Integer.MAX_VALUE);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });
    logConsumer.submit(
        () -> {
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx3"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx4"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx5"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx6"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx7"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx8"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx9"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx10"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx11"));*/

    // for (int i = 0; i < 10; i++) {
    TaskWorker taskWorker1 = logConsumer.getTaskWorker();
    taskWorker1.setTest(101 + "");
    logConsumer.start();
    //  }
    TaskWorker taskWorker2 = logConsumer.getTaskWorker();
    taskWorker2.setTest(102 + "");
    logConsumer.start();
    TaskWorker taskWorker3 = logConsumer.getTaskWorker();
    taskWorker3.setTest(103 + "");
    logConsumer.start();
    TaskWorker taskWorker4 = logConsumer.getTaskWorker();
    taskWorker4.setTest(104 + "");
    logConsumer.start();
    TaskWorker taskWorker5 = logConsumer.getTaskWorker();
    taskWorker5.setTest(105 + "");
    logConsumer.start();
    System.out.println("xasdasd");
    try {
      Thread.sleep(9999999);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    // logConsumer.shutdown();
  }
}
