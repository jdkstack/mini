package test.tools;

import java.util.List;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.record.LogRecord;
import org.jdkstack.logging.mini.core.tool.StringBuilderPool;
import test.pattern.PatternFormatter;
import test.pattern.PatternParser;

public class Examples {

  private static final Recorder LOG = LogFactory.getRecorder(Examples.class);

  private static final String customPattern = "%d{yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSZ} [%t] %-5level %logger{0} - %msg%n";

  public static void main(final String[] args) throws Exception {

    final PatternParser parser = new PatternParser("Converter");
    List<PatternFormatter> formatters = parser.parse(Examples.customPattern);
    LogRecord record = new LogRecord();
    final int[] indices = new int[9];
    indices[0] = 1028;
    indices[1] = 1030;
    indices[2] = 1032;
    indices[3] = 1034;
    indices[4] = 1036;
    indices[5] = 1038;
    indices[6] = 1040;
    indices[7] = 1042;
    indices[8] = 1044;
    final Object[] objects = {
      StringBuilderPool.box(System.currentTimeMillis()),
      StringBuilderPool.box(2),
      "3",
      StringBuilderPool.box(4D),
      "5",
      "6",
      StringBuilderPool.box(7F),
      StringBuilderPool.box(8),
      "9"
    };

    record.setMessage(
        "AAA1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901212345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012AAAA{}{}{}{}{}{}{}{}{}");
    record.setName("xxxxxxxx");
    record.setLogLevel("FATAL");
    record.setArgs1(1);
    record.setArgs2(2);
    record.setArgs3(3);
    record.setArgs4(4);
    record.setArgs5(5);
    record.setArgs6(6);
    record.setArgs7(7);
    record.setArgs8(8);
    record.setArgs9(9);
    record.setPaths(881, 0);
    record.setPaths(883, 1);
    record.setPaths(885, 2);
    record.setPaths(887, 3);
    record.setPaths(889, 4);
    record.setPaths(891, 5);
    record.setPaths(893, 6);
    record.setPaths(895, 7);
    record.setPaths(897, 8);

    record.setParams(1, 0);
    record.setParams(2, 1);
    record.setParams(3, 2);
    record.setParams(4, 3);
    record.setParams(5, 4);
    record.setParams(6, 5);
    record.setParams(7, 6);
    record.setParams(8, 7);
    record.setParams(9, 8);
    record.setPlaceholderCount(9);
    record.setThrown(new RuntimeException("xxx"));

    final StringBuilder sb = new StringBuilder();
    for (final PatternFormatter formatter : formatters) {
      formatter.format(record, sb);
    }

    System.out.println(sb);

    /*for (int k = 0; k < 1000; k++) {
      final long s = System.currentTimeMillis();
      for (int i = 0; i < 1000000; i++) {
        LOG.log(
            Constants.FATAL,
            null,
            record.getMessage(),
            "((Boolean)true).booleanValue()",
            "((Integer)Integer.MAX_VALUE).intValue()",
            "Short.MAX_VALUE",
            "3.999d",
            "4.09f",
            StringBuilderPool.box(888888),
            StringBuilderPool.box(6),
            StringBuilderPool.box(7),
            StringBuilderPool.box(8777777));
       }
      final long e = System.currentTimeMillis();
      //System.out.println(e - s);
    }*/
/*    try {
      Thread.sleep(100000);
    } catch (final InterruptedException ex) {
      throw new RuntimeException(ex);
    }*/
  }
}
