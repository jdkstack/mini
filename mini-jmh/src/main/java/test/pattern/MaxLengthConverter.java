package test.pattern;

import java.util.List;
import org.jdkstack.logging.mini.core.record.LogRecord;

public final class MaxLengthConverter extends LogEventPatternConverter {

  private final List<PatternFormatter> formatters;
  private final int maxLength;

  private MaxLengthConverter(final List<PatternFormatter> formatters, final int maxLength) {
    super("MaxLength", "maxLength");
    this.maxLength = maxLength;
    this.formatters = formatters;
  }

  @Override
  public void format(final LogRecord event, final StringBuilder toAppendTo) {
    final int initialLength = toAppendTo.length();
    for (int i = 0; i < formatters.size(); i++) {
      final PatternFormatter formatter = formatters.get(i);
      formatter.format(event, toAppendTo);
      if (toAppendTo.length() > initialLength + maxLength) { // stop early
        break;
      }
    }
    if (toAppendTo.length() > initialLength + maxLength) {
      toAppendTo.setLength(initialLength + maxLength);
      if (maxLength > 20) { // only append ellipses if length is not very short
        toAppendTo.append("...");
      }
    }
  }
}
