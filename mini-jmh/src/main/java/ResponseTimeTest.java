import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.HdrHistogram.Histogram;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.level.Constants;

// https://groups.google.com/g/mechanical-sympathy/c/0gaBXxFm4hE/m/O9QomwHIJAAJ
public class ResponseTimeTest {
  private static final Recorder LOG = LogFactory.getRecorder(ResponseTimeTest.class);
  private static final String LATENCY_MSG = new String(new char[64]);

  public static void main(final String[] args) throws Exception {

    final int threadCount = 1;
    final double loadMessagesPerSec = 100000;
    LOG.log(Constants.INFO, "start!");
    Thread.sleep(100);
    List<Histogram> serviceTmHistograms2 = new ArrayList<>(1);
    List<Histogram> responseTmHistograms2 = new ArrayList<>(1);
    final int requiredProcessors = threadCount + 1 + 1; // producers + 1 consumer + 1 for OS
    // Warmup: run as many iterations of 50,000 calls to logger.log as we can in 1 minute
    final long WARMUP_DURATION_MILLIS = TimeUnit.MINUTES.toMillis(1);
    final int WARMUP_COUNT = 50000 / threadCount;
    runLatencyTest(
        WARMUP_DURATION_MILLIS,
        WARMUP_COUNT,
        loadMessagesPerSec,
        serviceTmHistograms2,
        responseTmHistograms2,
        threadCount);
    System.out.println("-----------------Warmup done. load=" + loadMessagesPerSec);

    System.out.println("-----------------Starting measured run. load=" + loadMessagesPerSec);

    final long start = System.currentTimeMillis();
    List<Histogram> serviceTmHistograms = new ArrayList<>(1);
    List<Histogram> responseTmHistograms = new ArrayList<>(1);
    // Actual test: run as many iterations of 1,000,000 calls to logger.log as we can in 4 minutes.
    final long TEST_DURATION_MILLIS = TimeUnit.MINUTES.toMillis(1);
    final int COUNT = (1000 * 1000) / threadCount;
    runLatencyTest(
        TEST_DURATION_MILLIS,
        COUNT,
        loadMessagesPerSec,
        serviceTmHistograms,
        responseTmHistograms,
        threadCount);

    final long end = System.currentTimeMillis();

    // ... and report the results
    final Histogram resultServiceTm = createResultHistogram(serviceTmHistograms, start, end);
    resultServiceTm.outputPercentileDistribution(System.out, 1000.0);
    writeToFile("s", resultServiceTm, (int) (loadMessagesPerSec / 1000), 1000.0);

    final Histogram resultResponseTm = createResultHistogram(responseTmHistograms, start, end);
    resultResponseTm.outputPercentileDistribution(System.out, 1000.0);
    writeToFile("r", resultResponseTm, (int) (loadMessagesPerSec / 1000), 1000.0);
    System.out.printf("%n%s: %d threads, load %,f msg/sec", "x", threadCount, loadMessagesPerSec);
    System.out.println("Test duration: " + (end - start) / 1000.0 + " seconds");
  }

  private static void writeToFile(
      final String suffix, final Histogram hist, final int thousandMsgPerSec, final double scale)
      throws IOException {
    try (PrintStream pout =
        new PrintStream(new FileOutputStream(thousandMsgPerSec + "k" + suffix))) {
      hist.outputPercentileDistribution(pout, scale);
    }
  }

  private static Histogram createResultHistogram(
      final List<Histogram> list, final long start, final long end) {
    final Histogram result = new Histogram(TimeUnit.SECONDS.toNanos(10), 3);
    result.setStartTimeStamp(start);
    result.setEndTimeStamp(end);
    for (final Histogram hist : list) {
      result.add(hist);
    }
    return result;
  }

  public static void runLatencyTest(
      final long durationMillis,
      final int samples,
      final double loadMessagesPerSec,
      final List<Histogram> serviceTmHistograms,
      final List<Histogram> responseTmHistograms,
      final int threadCount)
      throws InterruptedException {
    final Histogram serviceTmHist = new Histogram(TimeUnit.SECONDS.toNanos(10), 3);
    final Histogram responseTmHist = new Histogram(TimeUnit.SECONDS.toNanos(10), 3);
    serviceTmHistograms.add(serviceTmHist);
    responseTmHistograms.add(responseTmHist);

    // 线程组.
    final Thread[] threads = new Thread[threadCount];
    // 信号量.
    final CountDownLatch LATCH = new CountDownLatch(threadCount);
    for (int i = 0; i < threadCount; i++) {
      threads[i] =
          new Thread("latencytest-" + i) {
            @Override
            public void run() {
              LATCH.countDown();
              try {
                LATCH.await(); // wait until all threads are ready to go
              } catch (final InterruptedException e) {
                interrupt();
                return;
              }
              final long endTimeMillis = System.currentTimeMillis() + durationMillis;
              do {
                final Pacer pacer = new Pacer(loadMessagesPerSec);
                runLatencyTest(samples, pacer, serviceTmHist, responseTmHist);
              } while (System.currentTimeMillis() < endTimeMillis);
            }
          };
      threads[i].start();
    }
    for (int i = 0; i < threadCount; i++) {
      threads[i].join();
    }
  }

  private static void runLatencyTest(
      final int samples,
      final Pacer pacer,
      final Histogram serviceTmHist,
      final Histogram responseTmHist) {

    for (int i = 0; i < samples; i++) {
      final long expectedStartTimeNanos = pacer.expectedNextOperationNanoTime();
      pacer.acquire(1);
      final long actualStartTime = System.nanoTime();
      LOG.log(Constants.INFO, LATENCY_MSG);
      final long doneTime = System.nanoTime();
      serviceTmHist.recordValue(doneTime - actualStartTime);
      responseTmHist.recordValue(doneTime - expectedStartTimeNanos);
    }
  }

  /**
   * Pacer determines the pace at which measurements are taken. Sample usage:
   *
   * <p>
   *
   * <pre>
   * - each thread has a Pacer instance
   * - at start of test, call pacer.setInitialStartTime(System.nanoTime());
   * - loop:
   *   - store result of pacer.expectedNextOperationNanoTime() as expectedStartTime
   *   - pacer.acquire(1);
   *   - before the measured operation: store System.nanoTime() as actualStartTime
   *   - perform the measured operation
   *   - store System.nanoTime() as doneTime
   *   - serviceTimeHistogram.recordValue(doneTime - actualStartTime);
   *   - responseTimeHistogram.recordValue(doneTime - expectedStartTime);
   * </pre>
   *
   * <p>Borrowed with permission from Gil Tene's Cassandra stress test:
   * https://github.com/LatencyUtils/cassandra-stress2/blob/trunk/tools/stress/src/org/apache/cassandra/stress/StressAction.java#L374
   */
  static class Pacer {
    private long initialStartTime;
    private double throughputInUnitsPerNsec;
    private long unitsCompleted;

    private boolean caughtUp = true;
    private long catchUpStartTime;
    private long unitsCompletedAtCatchUpStart;
    private double catchUpThroughputInUnitsPerNsec;
    private double catchUpRateMultiple;

    public Pacer(final double unitsPerSec) {
      this(unitsPerSec, 3.0); // Default to catching up at 3x the set throughput
    }

    public Pacer(final double unitsPerSec, final double catchUpRateMultiple) {
      setThroughout(unitsPerSec);
      setCatchupRateMultiple(catchUpRateMultiple);
      initialStartTime = System.nanoTime();
    }

    public void setInitialStartTime(final long initialStartTime) {
      this.initialStartTime = initialStartTime;
    }

    public void setThroughout(final double unitsPerSec) {
      throughputInUnitsPerNsec = unitsPerSec / 1000000000.0;
      catchUpThroughputInUnitsPerNsec = catchUpRateMultiple * throughputInUnitsPerNsec;
    }

    public void setCatchupRateMultiple(final double multiple) {
      catchUpRateMultiple = multiple;
      catchUpThroughputInUnitsPerNsec = catchUpRateMultiple * throughputInUnitsPerNsec;
    }

    /**
     * @return the time for the next operation
     */
    public long expectedNextOperationNanoTime() {
      return initialStartTime + (long) (unitsCompleted / throughputInUnitsPerNsec);
    }

    public long nsecToNextOperation() {

      final long now = System.nanoTime();

      long nextStartTime = expectedNextOperationNanoTime();

      boolean sendNow = true;

      if (nextStartTime > now) {
        // We are on pace. Indicate caught_up and don't send now.}
        caughtUp = true;
        sendNow = false;
      } else {
        // We are behind
        if (caughtUp) {
          // This is the first fall-behind since we were last caught up
          caughtUp = false;
          catchUpStartTime = now;
          unitsCompletedAtCatchUpStart = unitsCompleted;
        }

        // Figure out if it's time to send, per catch up throughput:
        final long unitsCompletedSinceCatchUpStart = unitsCompleted - unitsCompletedAtCatchUpStart;

        nextStartTime =
            catchUpStartTime
                + (long) (unitsCompletedSinceCatchUpStart / catchUpThroughputInUnitsPerNsec);

        if (nextStartTime > now) {
          // Not yet time to send, even at catch-up throughout:
          sendNow = false;
        }
      }

      return sendNow ? 0 : (nextStartTime - now);
    }

    /**
     * Will wait for next operation time. After this the expectedNextOperationNanoTime() will move
     * forward.
     *
     * @param unitCount
     */
    public void acquire(final long unitCount) {
      final long nsecToNextOperation = nsecToNextOperation();
      if (nsecToNextOperation > 0) {
        sleepNs(nsecToNextOperation);
      }
      unitsCompleted += unitCount;
    }

    private void sleepNs(final long ns) {
      long now = System.nanoTime();
      final long deadline = now + ns;
      while ((now = System.nanoTime()) < deadline) {
        // Thread.yield();
      }
    }
  }
}
