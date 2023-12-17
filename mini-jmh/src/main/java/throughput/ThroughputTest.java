package throughput;

import java.util.concurrent.TimeUnit;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;

public class ThroughputTest {

  private static final String LINE100 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!\"#$%&'()-=^~|\\@`[]{};:+*,.<>/?_123456";
  private static final String THROUGHPUT_MSG = LINE100 + LINE100 + LINE100 + LINE100 + LINE100;
  private static final Recorder LOG = LogFactory.getRecorder(ThroughputTest.class);

  public static void main(final String[] args) throws Exception {
    final String name = "mt";
    final String resultFile = "./file.log";
    final int threadCount = 2;
    System.out.printf("Starting %s %s (%d)...%n", ThroughputTest.class.getSimpleName(), name, threadCount);
    System.out.println("Warming up...");
    final long t1 = System.nanoTime();
    multiThread2(name, threadCount, resultFile);
    System.out.printf("Warmup complete in %.1f seconds%n", (System.nanoTime() - t1) / (1000.0 * 1000.0 * 1000.0));
    System.out.println("Waiting 10 seconds...");
    Thread.sleep(10000);
    System.out.printf("Starting %s %s (%d)...%n", ThroughputTest.class.getSimpleName(), name, threadCount);
    System.out.println("Main test...");
    final long t2 = System.nanoTime();
    multiThread(name, threadCount, resultFile);
    System.out.printf("Main test complete in %.1f seconds%n", (System.nanoTime() - t2) / (1000.0 * 1000.0 * 1000.0));
    Thread.sleep(99999);
  }

  private static void multiThread2(String name, int threadCount, String resultFile) throws InterruptedException {
    final Histogram warmupHist = createHistogram();
    final long stop = System.nanoTime() + TimeUnit.MINUTES.toNanos(1);
    final Runnable run1 = () -> {
      for (int i = 0; i < 10; i++) {
        final int LINES = 50000;
        final long s1 = System.currentTimeMillis();
        for (int j = 0; j < LINES; j++) {
          LOG.log("ERROR", THROUGHPUT_MSG);
        }
        final long s2 = System.currentTimeMillis();
        final long opsPerSec = (long) (LINES / ((double) (s2 - s1) / 1000));
        warmupHist.addObservation(opsPerSec);
        if (i > 0 && System.nanoTime() - stop >= 0) {
          return;
        }
      }
    };
    final Thread thread1 = new Thread(run1);
    final Thread thread2 = new Thread(run1);
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();
  }

  public static void multiThread(final String name, final int threadCount, final String resultFile) throws Exception {
    final Histogram[] histograms = new Histogram[threadCount];
    for (int i = 0; i < histograms.length; i++) {
      histograms[i] = createHistogram();
    }
    final int LINES = 100000;
    final Thread[] threads = new Thread[threadCount];
    for (int i = 0; i < threads.length; i++) {
      final Histogram histogram = histograms[i];
      threads[i] = new Thread() {
        @Override
        public void run() {
          final long s1 = System.currentTimeMillis();
          for (int j = 0; j < (LINES / threadCount); j++) {
            LOG.log("ERROR", THROUGHPUT_MSG);
          }
          final long s2 = System.currentTimeMillis();
          final long opsPerSec = (long) ((LINES / threadCount) / ((double) (s2 - s1) / 1000));
          histogram.addObservation(opsPerSec);
        }
      };
    }
    for (final Thread thread : threads) {
      thread.start();
    }
    for (final Thread thread : threads) {
      thread.join();
    }

    for (final Histogram histogram : histograms) {
      final String result = histogram.getMax() + " operations/second";
      System.out.println(result);
    }
  }

  static Histogram createHistogram() {
    final long[] intervals = new long[31];
    long intervalUpperBound = 1L;
    for (int i = 0, size = intervals.length - 1; i < size; i++) {
      intervalUpperBound *= 2;
      intervals[i] = intervalUpperBound;
    }

    intervals[intervals.length - 1] = Long.MAX_VALUE;
    return new Histogram(intervals);
  }
}
