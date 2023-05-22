package org.jdkstack.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {

  public static void main(String[] args) {
    ExecutorService logConsumer =
        new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx1"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx2"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx3"));
    logConsumer.submit(() -> System.out.println("xxxxxxxxxxx4"));
    logConsumer.submit(new Runnable() {
      @Override
      public void run() {

      }
    });
    logConsumer.shutdown();
  }
}
