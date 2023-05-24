package org.jdkstack.logging.mini.api.resource;

import org.jdkstack.logging.mini.api.record.Record;

import java.nio.Buffer;
import java.nio.CharBuffer;

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
   * @param logRecord .
   * @return StringBuilder .
   * @author admin
   */
  Buffer formatter(String formatterName, Record logRecord);
}
