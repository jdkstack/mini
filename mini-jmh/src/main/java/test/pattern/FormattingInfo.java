package test.pattern;

public final class FormattingInfo {

  /**
   * Empty array.
   */
  public static final FormattingInfo[] EMPTY_ARRAY = {};
  /**
   * Array of spaces.
   */
  private static final char[] SPACES = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
  /**
   * Array of zeros.
   */
  private static final char[] ZEROS = new char[]{'0', '0', '0', '0', '0', '0', '0', '0'};
  /**
   * Default instance.
   */
  private static final FormattingInfo DEFAULT = new FormattingInfo(false, 0, Integer.MAX_VALUE, true);
  /**
   * Minimum length.
   */
  private final int minLength;
  /**
   * Maximum length.
   */
  private final int maxLength;
  /**
   * Alignment.
   */
  private final boolean leftAlign;
  /**
   * Left vs. right-hand side truncation.
   */
  private final boolean leftTruncate;
  /**
   * Use zero-padding instead whitespace padding
   */
  private final boolean zeroPad;

  public FormattingInfo(final boolean leftAlign, final int minLength, final int maxLength, final boolean leftTruncate) {
    this(leftAlign, minLength, maxLength, leftTruncate, false);
  }

  public FormattingInfo(final boolean leftAlign, final int minLength, final int maxLength, final boolean leftTruncate, final boolean zeroPad) {
    this.leftAlign = leftAlign;
    this.minLength = minLength;
    this.maxLength = maxLength;
    this.leftTruncate = leftTruncate;
    this.zeroPad = zeroPad;
  }

  public static FormattingInfo getDefault() {
    return DEFAULT;
  }

  public boolean isLeftAligned() {
    return leftAlign;
  }

  public boolean isLeftTruncate() {
    return leftTruncate;
  }

  public boolean isZeroPad() {
    return zeroPad;
  }

  public int getMinLength() {
    return minLength;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public void format(final int fieldStart, final StringBuilder buffer) {
    final int rawLength = buffer.length() - fieldStart;

    if (rawLength > maxLength) {
      if (leftTruncate) {
        buffer.delete(fieldStart, buffer.length() - maxLength);
      } else {
        buffer.delete(fieldStart + maxLength, fieldStart + buffer.length());
      }
    } else if (rawLength < minLength) {
      if (leftAlign) {
        final int fieldEnd = buffer.length();
        buffer.setLength(fieldStart + minLength);

        for (int i = fieldEnd; i < buffer.length(); i++) {
          buffer.setCharAt(i, ' ');
        }
      } else {
        int padLength = minLength - rawLength;

        final char[] paddingArray = zeroPad ? ZEROS : SPACES;

        for (; padLength > paddingArray.length; padLength -= paddingArray.length) {
          buffer.insert(fieldStart, paddingArray);
        }

        buffer.insert(fieldStart, paddingArray, 0, padLength);
      }
    }
  }
}
