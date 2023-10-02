package org.jdkstack.logging.mini.core.filter;

import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.record.Record;

public class HandlerProduceFilter implements Filter {

  /** . */
  private final String key;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public HandlerProduceFilter(final LogRecorderContext context, final String key) {
    this.key = key;
  }

  @Override
  public boolean doFilter(Record logRecord) {
    return true;
  }
}
