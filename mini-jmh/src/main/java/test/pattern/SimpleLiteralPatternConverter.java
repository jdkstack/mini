package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

abstract class SimpleLiteralPatternConverter extends LogEventPatternConverter implements ArrayPatternConverter {

  private SimpleLiteralPatternConverter() {
    super("SimpleLiteral", "literal");
  }

  static LogEventPatternConverter of(final String literal, final boolean convertBackslashes) {
    String value = literal; // convertBackslashes ? OptionConverter.convertSpecialChars(literal) : literal;
    return of(value);
  }

  static LogEventPatternConverter of(final String literal) {
    if (literal == null || literal.isEmpty()) {
      return Noop.INSTANCE;
    }
    if (" ".equals(literal)) {
      return Space.INSTANCE;
    }
    return new StringValue(literal);
  }

  @Override
  public final void format(final LogRecord ignored, final StringBuilder output) {
    format(output);
  }

  @Override
  public final void format(final Object ignored, final StringBuilder output) {
    format(output);
  }

  @Override
  public final void format(final StringBuilder output, final Object... args) {
    format(output);
  }

  abstract void format(final StringBuilder output);

  @Override
  public final boolean isVariable() {
    return false;
  }

  @Override
  public final boolean handlesThrowable() {
    return false;
  }

  private static final class Noop extends SimpleLiteralPatternConverter {

    private static final Noop INSTANCE = new Noop();

    @Override
    void format(final StringBuilder output) {
      // no-op
    }
  }

  private static final class Space extends SimpleLiteralPatternConverter {

    private static final Space INSTANCE = new Space();

    @Override
    void format(final StringBuilder output) {
      output.append(' ');
    }
  }

  private static final class StringValue extends SimpleLiteralPatternConverter {

    private final String literal;

    StringValue(final String literal) {
      this.literal = literal;
    }

    @Override
    void format(final StringBuilder output) {
      output.append(literal);
    }
  }
}
