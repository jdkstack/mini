package org.jdkstack.logging.mini.core.ringbuffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;

/**
 * RandomAccessFile环形数组，用来缓存RandomAccessFile.
 *
 * <p>每次从Object[]循环获取一个RandomAccessFile。
 *
 * @author admin
 */
public class RandomAccessFileRingBuffer implements RingBuffer<RandomAccessFile> {

  /**
   * .
   */
  private final RandomAccessFile[] rb;

  /**
   * .
   */
  private final FileChannel[] rb2;

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
  public RandomAccessFileRingBuffer(final RingBuffer<File> buffer, final int capacity) {
    final int size = Power2.power2(capacity);
    this.mask = size - 1;
    this.rb = new RandomAccessFile[size];
    this.rb2 = new FileChannel[size];
    for (int i = 0; i < size; i++) {
      RandomAccessFile file = null;
      try {
        file = new RandomAccessFile(buffer.poll(), "rw");
      } catch (FileNotFoundException ignore) {
        //
      }
      this.rb[i] = file;
      final FileChannel channel = file.getChannel();
      try {
        //  清空文件，并初始化内部对象.
        channel.truncate(0);
      } catch (IOException ignore) {
        //
      }
      this.rb2[i] = channel;
    }
  }

  @Override
  public final RandomAccessFile poll() {
    final int index = this.current++;
    final RandomAccessFile result = this.rb[this.mask & index];
    final FileChannel channel = this.rb2[this.mask & index];
    try {
      //  清空文件，不需要初始化内部对象.
      channel.truncate(0);
    } catch (IOException ignore) {
      //
    }
    return result;
  }
}
