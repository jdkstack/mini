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
public interface ForFactory<T> extends Factory<T> {

  /**
   * .
   *
   * <p>.
   *
   * @param formatterName .
   * @param logRecord     .
   * @return StringBuilder .
   * @author admin
   */
  StringBuilder formatter(String formatterName, Record logRecord);
}
