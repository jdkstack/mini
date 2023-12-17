package test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.tool.StringBuilderPool;
import org.jdkstack.logging.mini.extension.factory.AuditLogFactory;

public class Examples {

  /** 诊断日志. */
  private static final Recorder LOG = LogFactory.getRecorder(Examples.class);
  /** 审计日志. */
  private static final Recorder AUDIT_LOG = AuditLogFactory.getRecorder(Examples.class);

  public static void main(final String[] args) throws Exception {
    //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100), new CallerRunsPolicy());
    String message = "AAA1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901212345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012AAAA{}{}{}{}{}{}{}{}{}";
    for (int k = 0; k < 10; k++) {
      final long s = System.currentTimeMillis();
      for (int i = 0; i < 1000000; i++) {
        final int j = i;
        AUDIT_LOG.log(Constants.INFO, message, StringBuilderPool.box(j));
        //threadPoolExecutor.submit(() -> LOG.log(Constants.FATAL, message, StringBuilderPool.box(j)));
        LOG.log(Constants.FATAL, message, StringBuilderPool.box(i));
      }
      final long e = System.currentTimeMillis();
      System.out.println(e - s);
    }
    Thread.sleep(99999999);
  }
}