package test.pattern;

import java.util.Locale;
import java.util.Map;
import org.jdkstack.logging.mini.core.level.LogLevel;
import org.jdkstack.logging.mini.core.record.LogRecord;

public class LevelPatternConverter extends LogEventPatternConverter {
  private static final String OPTION_LENGTH = "length";
  private static final String OPTION_LOWER = "lowerCase";

  private static final LevelPatternConverter INSTANCE = new SimpleLevelPatternConverter();

  private LevelPatternConverter() {
    super("Level", "level");
  }

  public static LevelPatternConverter newInstance(final String[] options) {
    if (options == null || options.length == 0) {
      return INSTANCE;
    }
    /*        final Map<Level, String> levelMap = new HashMap<>();
    int length = Integer.MAX_VALUE; // More than the longest level name.
    boolean lowerCase = false;
    final String[] definitions = options[0].split(Patterns.COMMA_SEPARATOR);
    for (final String def : definitions) {
        final String[] pair = def.split("=");
        if (pair == null || pair.length != 2) {
            continue;
        }
        final String key = pair[0].trim();
        final String value = pair[1].trim();
        if (OPTION_LENGTH.equalsIgnoreCase(key)) {
            length = Integers.parseInt(value);
        } else if (OPTION_LOWER.equalsIgnoreCase(key)) {
            lowerCase = Boolean.parseBoolean(value);
        } else {
            final Level level = Level.toLevel(key, null);
            if (level == null) {
            } else {
                levelMap.put(level, value);
            }
        }
    }
    if (levelMap.isEmpty() && length == Integer.MAX_VALUE && !lowerCase) {
        return INSTANCE;
    }
    for (final Level level : Level.values()) {
        if (!levelMap.containsKey(level)) {
            final String left = left(level, length);
            levelMap.put(level, lowerCase ? left.toLowerCase(Locale.US) : left);
        }
    }
    return new LevelMapLevelPatternConverter(levelMap);*/

    return null;
  }

  private static String left(final LogLevel level, final int length) {
    final String string = level.toString();
    if (length >= string.length()) {
      return string;
    }
    return string.substring(0, length);
  }

  @Override
  public void format(final LogRecord event, final StringBuilder output) {
    throw new UnsupportedOperationException("Overridden by subclasses");
  }

  @Override
  public String getStyleClass(final Object e) {
    if (e instanceof LogRecord) {
      return "level " + ((LogRecord) e).getLevel().toLowerCase(Locale.ENGLISH);
    }
    return "level";
  }

  private static final class SimpleLevelPatternConverter extends LevelPatternConverter {

    @Override
    public void format(final LogRecord event, final StringBuilder output) {
      output.append(event.getLevel());
    }
  }

  private static final class LevelMapLevelPatternConverter extends LevelPatternConverter {

    private final Map<LogLevel, String> levelMap;

    private LevelMapLevelPatternConverter(final Map<LogLevel, String> levelMap) {
      this.levelMap = levelMap;
    }

    @Override
    public void format(final LogRecord event, final StringBuilder output) {
      output.append(levelMap.get(event.getLevel()));
    }
  }
}
