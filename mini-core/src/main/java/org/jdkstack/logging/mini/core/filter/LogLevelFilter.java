package org.jdkstack.logging.mini.core.filter;

import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.record.Record;

public class LogLevelFilter implements Filter {

  @Override
  public final boolean doFilter(final Record logRecord) {
    final String levelName = logRecord.getLevelName();
    return "INFO".equals(levelName);
  }
}
