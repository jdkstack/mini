package org.jdkstack.pool.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {

  public static void main(String[] args) {
    ExecutorService logConsumer =
        new ThreadPoolExecutor(
            2,
            2,
            0,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            (r, executor) -> {
              System.out.println("xxxxxxxx");
            });
    logConsumer.submit(
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
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx11"));

    System.out.println("xasdasd");
    logConsumer.shutdown();
  }
}
