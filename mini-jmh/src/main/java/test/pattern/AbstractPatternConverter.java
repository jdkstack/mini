package test.pattern;

public abstract class AbstractPatternConverter implements PatternConverter {

  private final String name;

  private final String style;

  protected AbstractPatternConverter(final String name, final String style) {
    this.name = name;
    this.style = style;
  }

  @Override
  public final String getName() {
    return name;
  }

  @Override
  public String getStyleClass(final Object e) {
    return style;
  }
}
