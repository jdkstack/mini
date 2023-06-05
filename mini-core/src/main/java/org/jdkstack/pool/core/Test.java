package org.jdkstack.pool.core;

import java.util.concurrent.TimeUnit;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

public class Test {

  public static void main(String[] args) {
    ThreadPoolExecutor logConsumer =
        new ThreadPoolExecutor(
            2, 2, 0, TimeUnit.SECONDS, new MpmcBlockingQueueV3<>(1024, TaskWorker::new));
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

    for (;;) {
      TaskWorker taskWorker1 = (TaskWorker) logConsumer.getTaskWorker();
      taskWorker1.setTest("xxxx");
      logConsumer.start();
    }

/*    try {
      Thread.sleep(9999999);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }*/
    // logConsumer.shutdown();
  }
}
