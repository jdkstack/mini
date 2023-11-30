package test.pattern;

import org.jdkstack.logging.mini.core.record.LogRecord;

public final class LiteralPatternConverter extends LogEventPatternConverter implements ArrayPatternConverter {

  private String literal;

  private boolean substitute;

  public LiteralPatternConverter(final String literal, final boolean convertBackslashes) {
    super("Literal", "literal");
    this.literal = convertBackslashes ? convertSpecialChars(literal) : literal;

    substitute = containsSubstitutionSequence(literal);
  }

  static boolean containsSubstitutionSequence(final String literal) {
    return literal != null && literal.contains("${");
  }

  public static String convertSpecialChars(final String s) {
    char c;
    final int len = s.length();
    final StringBuilder sbuf = new StringBuilder(len);

    int i = 0;
    while (i < len) {
      c = s.charAt(i++);
      if (c == '\\') {
        c = s.charAt(i++);
        switch (c) {
          case 'n':
            c = '\n';
            break;
          case 'r':
            c = '\r';
            break;
          case 't':
            c = '\t';
            break;
          case 'f':
            c = '\f';
            break;
          case 'b':
            c = '\b';
            break;
          case '"':
            c = '\"';
            break;
          case '\'':
            c = '\'';
            break;
          case '\\':
            c = '\\';
            break;
          default:
            // there is no default case.
        }
      }
      sbuf.append(c);
    }
    return sbuf.toString();
  }

  @Override
  public void format(final LogRecord event, final StringBuilder toAppendTo) {
    // toAppendTo.append(substitute ? config.getStrSubstitutor().replace(event, literal) : literal);
  }

  @Override
  public void format(final Object obj, final StringBuilder output) {
    // output.append(substitute ? config.getStrSubstitutor().replace(literal) : literal);
  }

  @Override
  public void format(final StringBuilder output, final Object... objects) {
    //  output.append(substitute ? config.getStrSubstitutor().replace(literal) : literal);
  }

  public String getLiteral() {
    return literal;
  }

  @Override
  public boolean isVariable() {
    return false;
  }
}
