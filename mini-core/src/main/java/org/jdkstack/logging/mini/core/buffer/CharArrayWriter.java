package org.jdkstack.logging.mini.core.buffer;

import java.io.BufferedWriter;

/**
 * .
 *
 * <p>将char[]字符数组写入文件.
 *
 * @author admin
 */
public class CharArrayWriter extends AbstractCharArrayWriter {

  /** . */
  private BufferedWriter bufferedWriter;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param bytes b.
   * @param offset o.
   * @param length l.
   * @author admin
   */
  @Override
  public final void readToDestination(final char[] bytes, final int offset, final int length)
      throws Exception {
    this.bufferedWriter.write(bytes, offset, length);
    this.bufferedWriter.flush();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param obj obj.
   * @author admin
   */
  @Override
  public void setDestination(final Object obj) {
    this.bufferedWriter = (BufferedWriter) obj;
  }
}
