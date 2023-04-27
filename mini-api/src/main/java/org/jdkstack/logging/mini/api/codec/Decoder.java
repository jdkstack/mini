package org.jdkstack.logging.mini.api.codec;

import org.jdkstack.logging.mini.api.buffer.CharWriter;

/**
 * .
 *
 * <p>.
 *
 * @param <T> .
 * @author admin
 */
public interface Decoder<T> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source .
   * @param reader .
   * @author admin
   */
  void decode(T source, CharWriter reader) throws Exception;
}
