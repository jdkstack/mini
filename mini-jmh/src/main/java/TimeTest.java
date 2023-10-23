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

/**
 * 业务时间serviceTime，延迟时间latencyTime，响应时间responseTime.
 *
 * <p>业务时间+延迟时间=响应时间。
 *
 * <pre>
 *     <a href="https://groups.google.com/g/mechanical-sympathy/c/0gaBXxFm4hE/m/O9QomwHIJAAJ">延迟时间算法</a>.
 * </pre>
 *
 * @author admin
 */
public class TimeTest {
  private static final Recorder LOG = LogFactory.getRecorder(TimeTest.class);

  private static final String LATENCY_MSG = new String(new char[64]);

  public static void main(final String[] args) throws Exception {
    // 线程数量。
    final int threadCount = 1;
    // 每秒要执行多少次业务方法，execute times per second。
    final int eps = 100000;

    final long start1 = System.currentTimeMillis();
    List<Histogram> serviceTimeWarm = new ArrayList<>(1);
    List<Histogram> responseTimeWarm = new ArrayList<>(1);
    List<Histogram> latencyTimeWarm = new ArrayList<>(1);
    // 对业务方法预热一分钟，执行一分钟时间。
    final long warmTime = TimeUnit.MINUTES.toMillis(1);
    // 单个线程执行多少次业务方法（平均分配）。
    final int warmCount = 50000 / threadCount;
    runLatencyTest(
        warmTime, warmCount, eps, serviceTimeWarm, responseTimeWarm, latencyTimeWarm, threadCount);
    final long end1 = System.currentTimeMillis();
    System.out.printf("%n%s: %d threads, load %,f msg/sec", "Warm", threadCount, (float) eps);
    System.out.println(" ,Warm duration: " + (end1 - start1) / 1000.0 + " seconds");

    final long start = System.currentTimeMillis();
    List<Histogram> serviceTime = new ArrayList<>(1);
    List<Histogram> responseTime = new ArrayList<>(1);
    List<Histogram> latencyTime = new ArrayList<>(1);
    // 正式测试一分钟业务方法。
    final long testTime = TimeUnit.MINUTES.toMillis(1);
    //
    final int count = 1000000 / threadCount;
    runLatencyTest(testTime, count, eps, serviceTime, responseTime, latencyTime, threadCount);
    final long end = System.currentTimeMillis();

    System.out.printf("%n%s: %d threads, load %,f msg/sec", "Test", threadCount, (float) eps);
    System.out.println(" ,Test duration: " + (end - start) / 1000.0 + " seconds");
    System.out.println("响应时间：");
    final Histogram resultResponseTm = createResultHistogram(responseTime, start, end);
    resultResponseTm.outputPercentileDistribution(System.out, 1000.0);

    System.out.println("业务时间：");
    final Histogram resultServiceTm = createResultHistogram(serviceTime, start, end);
    resultServiceTm.outputPercentileDistribution(System.out, 1000.0);
    System.out.println("延迟时间：");
    final Histogram resultLatencyTm = createResultHistogram(latencyTime, start, end);
    resultLatencyTm.outputPercentileDistribution(System.out, 1000.0);
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
    final Histogram result = new Histogram(TimeUnit.SECONDS.toMillis(1000), 3);
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
      List<Histogram> latencyTmHistograms,
      final int threadCount)
      throws InterruptedException {
    final Histogram latencyTmHist = new Histogram(TimeUnit.SECONDS.toMillis(1000), 3);
    final Histogram serviceTmHist = new Histogram(TimeUnit.SECONDS.toMillis(1000), 3);
    final Histogram responseTmHist = new Histogram(TimeUnit.SECONDS.toMillis(1000), 3);
    serviceTmHistograms.add(serviceTmHist);
    responseTmHistograms.add(responseTmHist);
    latencyTmHistograms.add(latencyTmHist);

    // 线程组.
    final Thread[] threads = new Thread[threadCount];
    // 信号量.
    final CountDownLatch latch = new CountDownLatch(threadCount);
    for (int i = 0; i < threadCount; i++) {
      // 创建一个线程，并启动线程。
      threads[i] =
          new Thread("timeTest-" + i) {
            @Override
            public void run() {
              latch.countDown();
              try {
                latch.await(); // wait until all threads are ready to go
              } catch (final InterruptedException e) {
                interrupt();
                return;
              }
              // 未来时间毫秒数，作为结束测试的标志。
              final long endTimeMillis = System.currentTimeMillis() + durationMillis;
              // 为每一个线程绑定一个对象。内部处理了时间毫秒相关的算法。
              final Pacer pacer = new Pacer(loadMessagesPerSec);
              // 如果当前的时间毫秒数大于等于未来时间毫秒数，结束测试。
              do {
                for (int i = 0; i < samples; i++) {
                  // 一个时间延迟算法。
                  final long latencyTime = pacer.expectedNextOperationNanoTime();
                  // 计算下一个延迟时间。
                  pacer.acquire(1);
                  // 开始执行一次业务。
                  final long serviceStartTime = System.currentTimeMillis();
                  LOG.log(Constants.INFO, LATENCY_MSG);
                  // 结束执行一次业务。
                  final long serviceEndTime = System.currentTimeMillis();
                  // 业务时间毫秒。
                  serviceTmHist.recordValue(serviceEndTime - serviceStartTime);
                  // 响应时间毫秒。
                  responseTmHist.recordValue(serviceEndTime - latencyTime);
                  // 延时时间毫秒。
                  latencyTmHist.recordValue(serviceStartTime - latencyTime);
                }
              } while (System.currentTimeMillis() < endTimeMillis);
            }
          };
      threads[i].start();
    }

    // 所有线程执行完毕。
    for (int i = 0; i < threadCount; i++) {
      threads[i].join();
    }
  }
}
