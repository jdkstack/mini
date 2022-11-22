package org.jdkstack.logging.mini.core.filter;

import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.record.Record;

public class DateTimeFilter implements Filter {

  @Override
  public final boolean doFilter(final Record logRecord) {
    final long day = logRecord.getDay();
    return day > 10 && day < 20;
  }
}
