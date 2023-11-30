package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

public final class LoggerPatternConverter extends NamePatternConverter {

  private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);

  private LoggerPatternConverter(final String[] options) {
    super("Logger", "logger", options);
  }

  public static LoggerPatternConverter newInstance(final String[] options) {
    if (options == null || options.length == 0) {
      return INSTANCE;
    }

    return new LoggerPatternConverter(options);
  }

  @Override
  public void format(final LogRecord event, final StringBuilder toAppendTo) {
    abbreviate(event.getName(), toAppendTo);
  }
}
