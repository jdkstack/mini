package org.jdkstack.logging.mini.api.buffer;

import java.io.BufferedWriter;
import java.nio.CharBuffer;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public interface CharWriter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return CharBuffer .
   * @author admin
   */
  CharBuffer getCharBuffer();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param charBuf .
   * @author admin
   */
  void flush(CharBuffer charBuf);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void flush();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param bytes  .
   * @param offset .
   * @param length .
   * @author admin
   */
  void readToDestination(final char[] bytes, final int offset, final int length);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param bufferedWriter .
   * @author admin
   */
  void setBufferedWriter(BufferedWriter bufferedWriter);
}
