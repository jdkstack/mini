package org.jdkstack.logging.mini.api.codec;

import org.jdkstack.logging.mini.api.buffer.ByteWriter;

/**
 * .
 *
 * <p>.
 *
 * @param <T> .
 * @author admin
 */
public interface CodecEncoder<T> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source      .
   * @param destination .
   * @author admin
   */
  void encode(T source, ByteWriter destination) throws Exception;
}
