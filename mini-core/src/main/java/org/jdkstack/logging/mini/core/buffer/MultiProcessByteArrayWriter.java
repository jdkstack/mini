package org.jdkstack.logging.mini.core.buffer;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.tool.ThreadLocalTool;

public final class MultiProcessByteArrayWriter extends ByteArrayWriter {

  public void setLength(final int count) throws IOException {
    FileChannel channel = randomAccessFile.getChannel();
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    FileLock lock = logConsumeThread.getLock();
    try {
      randomAccessFile.setLength(Math.max(0, channel.size() - count));
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
    FileChannel channel = randomAccessFile.getChannel();
    final LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
    FileLock lock = logConsumeThread.getLock();
    try {
      channel.position(channel.size());
      randomAccessFile.write(bytes, offset, length);
    } finally {
      lock.release();
    }
  }
}
