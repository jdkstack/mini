package org.jdkstack.logging.mini.api.resource;

import org.jdkstack.logging.mini.api.record.Record;

/**
 * .
 *
 * <p>.
 *
 * @param <T> t .
 * @author admin
 */
public interface FilFactory<T> extends Factory<T> {

  /**
   * .
   *
   * <p>.
   *
   * @param filterName .
   * @param logRecord .
   * @return boolean .
   * @author admin
   */
  boolean filter(String filterName, Record logRecord);
}
