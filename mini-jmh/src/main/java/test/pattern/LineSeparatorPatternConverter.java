package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

public final class LineSeparatorPatternConverter extends LogEventPatternConverter {

  private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();

  private LineSeparatorPatternConverter() {
    super("Line Sep", "lineSep");
  }

  public static LineSeparatorPatternConverter newInstance(final String[] options) {
    return INSTANCE;
  }

  @Override
  public void format(final LogRecord ignored, final StringBuilder toAppendTo) {
    toAppendTo.append(System.lineSeparator());
  }

  @Override
  public void format(final Object ignored, final StringBuilder output) {
    output.append(System.lineSeparator());
  }

  @Override
  public boolean isVariable() {
    return false;
  }
}
