package test.pattern;

public interface ArrayPatternConverter extends PatternConverter {

  void format(final StringBuilder toAppendTo, Object... objects);
}
