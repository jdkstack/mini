package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

public class PatternFormatter {

  public static final PatternFormatter[] EMPTY_ARRAY = {};

  private final LogEventPatternConverter converter;
  private final FormattingInfo field;
  private final boolean skipFormattingInfo;

  public PatternFormatter(final LogEventPatternConverter converter, final FormattingInfo field) {
    this.converter = converter;
    this.field = field;
    this.skipFormattingInfo = field == FormattingInfo.getDefault();
  }

  public void format(final LogRecord event, final StringBuilder buf) {
    if (skipFormattingInfo) {
      converter.format(event, buf);
    } else {
      formatWithInfo(event, buf);
    }
  }

  private void formatWithInfo(final LogRecord event, final StringBuilder buf) {
    final int startField = buf.length();
    converter.format(event, buf);
    field.format(startField, buf);
  }

  public LogEventPatternConverter getConverter() {
    return converter;
  }

  public FormattingInfo getFormattingInfo() {
    return field;
  }

  public boolean handlesThrowable() {
    return converter.handlesThrowable();
  }

  public boolean requiresLocation() {
    return true; // converter instanceof LocationAware && ((LocationAware)
    // converter).requiresLocation();
  }
}
