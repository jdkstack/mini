package org.jdkstack.logging.mini.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.logging.jdkbean.core.annotation.Component;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.resource.LeFactory;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.level.LogLevel;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class LevelFactory implements LeFactory {

  /** . */
  private final Map<String, Level> levels = new ConcurrentHashMap<>(32);

  /**
   * .
   *
   * <p>.
   *
   * @author admin
   */
  public LevelFactory() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 日志级别的名称.
   * @return Level .
   * @author admin
   */
  @Override
  public final Level findLevel(final String name) {
    // 找到注册的日志级别。
    Level level = this.levels.get(name);
    if (null == level) {
      //创建一个临时的日志级别。
      level = new LogLevel(name, Constants.LEVEL_VALUE);
      this.levels.putIfAbsent(name, level);
    }
    return level;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 日志级别的名称.
   * @author admin
   */
  @Override
  public final void putLevel(final String name, final int value) {
    final Level level = new LogLevel(name, value);
    this.levels.put(name, level);
  }

  @Override
  public final boolean doFilter(final String logLevels, final Level maxLevel, final Level minLevel) {
    final Level level = this.findLevel(logLevels);
    // 只要有一个不是真,则表示日志会过滤掉.
    final boolean b2 = level.intValue() <= maxLevel.intValue();
    final boolean b1 = level.intValue() >= minLevel.intValue();
    return b2 && b1;
  }
}
