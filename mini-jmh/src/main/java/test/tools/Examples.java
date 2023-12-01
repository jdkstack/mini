package test.tools;

import java.util.List;
import org.jdkstack.logging.mini.core.record.LogRecord;
import test.pattern.PatternFormatter;
import test.pattern.PatternParser;

public class Examples {

  private static final String customPattern = "%d{yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSZ} [%t] %-5level %logger{0} - %msg%n";

  public static void main(final String[] args) throws Exception {
    final PatternParser parser = new PatternParser("Converter");
    List<PatternFormatter> formatters = parser.parse(Examples.customPattern);
    LogRecord record = new LogRecord();
    String message = "test";
    record.setMessageText(message);
    record.setName("xxxxxxxx");
    record.setLevelName("FATAL");
    record.setThrown(new RuntimeException("xxx"));
    final StringBuilder sb = new StringBuilder();
    for (final PatternFormatter formatter : formatters) {
      formatter.format(record, sb);
    }
    System.out.println(sb);
  }
}
