package test.pattern;

import java.util.ArrayList;
import java.util.List;
import org.jdkstack.logging.mini.core.record.LogRecord;

public class MessagePatternConverter extends LogEventPatternConverter {

  private static final String LOOKUPS = "lookups";
  private static final String NOLOOKUPS = "nolookups";

  private MessagePatternConverter() {
    super("Message", "message");
  }

  public static MessagePatternConverter newInstance(final String[] options) {
    String[] formats = withoutLookupOptions(options);

    MessagePatternConverter result = formats == null || formats.length == 0 ? SimpleMessagePatternConverter.INSTANCE : new FormattedMessagePatternConverter(formats);
    return result;
  }

  private static String[] withoutLookupOptions(final String[] options) {
    if (options == null || options.length == 0) {
      return options;
    }
    List<String> results = new ArrayList<>(options.length);
    for (String option : options) {
      if (LOOKUPS.equalsIgnoreCase(option) || NOLOOKUPS.equalsIgnoreCase(option)) {
      } else {
        results.add(option);
      }
    }
    return results.toArray(new String[]{});
  }

  @Override
  public void format(final LogRecord event, final StringBuilder toAppendTo) {
    throw new UnsupportedOperationException();
  }

  private static final class SimpleMessagePatternConverter extends MessagePatternConverter {

    private static final MessagePatternConverter INSTANCE = new SimpleMessagePatternConverter();

    @Override
    public void format(final LogRecord event, final StringBuilder toAppendTo) {
      toAppendTo.append(event.getMessageText());
    }
  }

  private static final class FormattedMessagePatternConverter extends MessagePatternConverter {

    private final String[] formats;

    FormattedMessagePatternConverter(final String[] formats) {
      this.formats = formats;
    }

    @Override
    public void format(final LogRecord event, final StringBuilder toAppendTo) {
      toAppendTo.append(event.getMessageText());
    }
  }

  private static final class RenderingPatternConverter extends MessagePatternConverter {

    private final MessagePatternConverter delegate;

    RenderingPatternConverter(final MessagePatternConverter delegate) {
      this.delegate = delegate;
    }

    @Override
    public void format(final LogRecord event, final StringBuilder toAppendTo) {
      StringBuilder workingBuilder = new StringBuilder(80);
      delegate.format(event, workingBuilder);
    }
  }
}
