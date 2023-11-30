package test.pattern;

/**
 * <em>Consider this class private.</em>
 */
public final class Chars {

  /**
   * Carriage Return.
   */
  public static final char CR = '\r';

  /**
   * Double Quote.
   */
  public static final char DQUOTE = '\"';

  /**
   * Equals '='.
   */
  public static final char EQ = '=';

  /**
   * Line Feed.
   */
  public static final char LF = '\n';

  /**
   * NUL.
   */
  public static final char NUL = 0;

  /**
   * Single Quote ['].
   */
  public static final char QUOTE = '\'';

  /**
   * Space.
   */
  public static final char SPACE = ' ';

  /**
   * Tab.
   */
  public static final char TAB = '\t';

  private Chars() {
  }

  /**
   * Converts a digit into an upper-case hexadecimal character or the null character if invalid.
   *
   * @param digit a number 0 - 15
   * @return the hex character for that digit or '\0' if invalid
   */
  public static char getUpperCaseHex(final int digit) {
    if (digit < 0 || digit >= 16) {
      return '\0';
    }
    return digit < 10 ? getNumericalDigit(digit) : getUpperCaseAlphaDigit(digit);
  }

  /**
   * Converts a digit into an lower-case hexadecimal character or the null character if invalid.
   *
   * @param digit a number 0 - 15
   * @return the hex character for that digit or '\0' if invalid
   */
  public static char getLowerCaseHex(final int digit) {
    if (digit < 0 || digit >= 16) {
      return '\0';
    }
    return digit < 10 ? getNumericalDigit(digit) : getLowerCaseAlphaDigit(digit);
  }

  private static char getNumericalDigit(final int digit) {
    return (char) ('0' + digit);
  }

  private static char getUpperCaseAlphaDigit(final int digit) {
    return (char) ('A' + digit - 10);
  }

  private static char getLowerCaseAlphaDigit(final int digit) {
    return (char) ('a' + digit - 10);
  }
}
