package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

public final class ThreadNamePatternConverter extends LogEventPatternConverter {
  private static final ThreadNamePatternConverter INSTANCE = new ThreadNamePatternConverter();

  private ThreadNamePatternConverter() {
    super("Thread", "thread");
  }

  public static ThreadNamePatternConverter newInstance(final String[] options) {
    return INSTANCE;
  }

  @Override
  public void format(final LogRecord event, final StringBuilder toAppendTo) {
    toAppendTo.append(Thread.currentThread().getName());
  }
}
