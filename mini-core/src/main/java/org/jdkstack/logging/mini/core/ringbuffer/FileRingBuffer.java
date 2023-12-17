package org.jdkstack.logging.mini.core.ringbuffer;

import java.io.File;
import java.io.IOException;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;

/**
 * Files环形数组，用来缓存File.
 *
 * <p>每次从Object[]循环获取一个Files。
 *
 * @author admin
 */
public class FileRingBuffer implements RingBuffer<File> {

  /**
   * .
   */
  private final File[] rb;

  /**
   * .
   */
  private final int mask;

  /**
   * .
   */
  private int current;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity .
   * @author admin
   */
  public FileRingBuffer(final File dir, final String fileName, final String fileNamePrefix, final int capacity) {
    // 创建目录。
    if (!dir.exists()) {
      dir.mkdirs();
    }
    final int size = Power2.power2(capacity);
    this.mask = size - 1;
    this.rb = new File[size];
    for (int i = 0; i < size; i++) {
      final File file = new File(dir, fileName + i + fileNamePrefix);
      try {
        file.createNewFile();
      } catch (IOException ignore) {
        //
      }
      this.rb[i] = file;
    }
  }

  @Override
  public final File poll() {
    final File result = this.rb[this.mask & this.current++];
    result.delete();
    return result;
  }
}