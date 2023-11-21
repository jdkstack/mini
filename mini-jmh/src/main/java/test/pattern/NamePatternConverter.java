package test.pattern;

public abstract class NamePatternConverter extends LogEventPatternConverter {

  private final NameAbbreviator abbreviator;

  protected NamePatternConverter(final String name, final String style, final String[] options) {
    super(name, style);

    if (options != null && options.length > 0) {
      abbreviator = NameAbbreviator.getAbbreviator(options[0]);
    } else {
      abbreviator = NameAbbreviator.getDefaultAbbreviator();
    }
  }

  protected final void abbreviate(final String original, final StringBuilder destination) {
    abbreviator.abbreviate(original, destination);
  }
}
