package test;

import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.tool.StringBuilderPool;

public class Examples {

  private static final Recorder LOG = LogFactory.getRecorder(Examples.class);

  public static void main(final String[] args) throws Exception {
    //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100), new CallerRunsPolicy());
    String message = "BBBAAA12345678901234567890123456789012345678901234567890123456789012AAAA0123456781234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567843BBBAAA12345678901234567890123{}";
    for (int k = 0; k < 10; k++) {
      final long s = System.currentTimeMillis();
      for (int i = 0; i < 1000000; i++) {
        final int j = i;
        //threadPoolExecutor.submit(() -> LOG.log(23, Constants.FATAL, message, StringBuilderPool.box(j)));
        LOG.log(Constants.FATAL, message, StringBuilderPool.box(i));
        // 第一个参数是当前行，当传递当前行时会输出堆栈信息。
        //LOG.log(26, Constants.FATAL, message, StringBuilderPool.box(i));
      }
      final long e = System.currentTimeMillis();
      System.out.println(e - s);
    }
    Thread.sleep(99999999);
  }
}
