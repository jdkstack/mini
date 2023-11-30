package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

public abstract class LogEventPatternConverter extends AbstractPatternConverter {

  protected LogEventPatternConverter(final String name, final String style) {
    super(name, style);
  }

  public abstract void format(final LogRecord event, final StringBuilder toAppendTo);

  @Override
  public void format(final Object obj, final StringBuilder output) {
    if (obj instanceof LogRecord) {
      format((LogRecord) obj, output);
    }
  }

  public boolean handlesThrowable() {
    return false;
  }

  public boolean isVariable() {
    return true;
  }
}
