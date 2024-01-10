package org.jdkstack.logging.mini.core.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public final class MultiProcessByteArrayWriter extends ByteArrayWriter {

  private RandomAccessFile file;

  public void setLength(final int count) throws IOException {
    FileChannel channel = file.getChannel();
    FileLock lock = channel.lock();
    try {
      file.setLength(Math.max(0, channel.size() - count));
    } finally {
      lock.release();
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param bytes  b.
   * @param offset o.
   * @param length l.
   * @author admin
   */
  @Override
  public void writeToDestination(final byte[] bytes, final int offset, final int length) throws Exception {
    this.size += length;
    this.line += 1;
    FileChannel channel = file.getChannel();
    FileLock lock = channel.lock();
    try {
      channel.position(channel.size());
      file.write(bytes, offset, length);
    } finally {
      lock.release();
    }
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
    this.size = 0L;
    this.line = 0L;
    this.randomAccessFile = (RandomAccessFile) obj;
  }
}
