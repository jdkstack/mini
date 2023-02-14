package org.jdkstack.logging.mini.core.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MmapByteArrayWriter extends AbstractByteArrayWriter {

  public static final long DEFAULT_REGION_LENGTH = 1L << 30;
  /** . */
  private RandomAccessFile randomAccessFile;

  private MappedByteBuffer mappedBuffer;


  @Override
  public void writeToDestination(final byte[] bytes, final int offset, final int length) {
    try {
      this.write(bytes, offset, length);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setRandomAccessFile(RandomAccessFile randomAccessFile) {
    this.randomAccessFile = randomAccessFile;
    remap();
  }

  public MappedByteBuffer mmap(final FileChannel fileChannel, final long start, final long size) {
    final MappedByteBuffer map;
    try {
      map = fileChannel.map(FileChannel.MapMode.READ_WRITE, start, size);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    map.order(ByteOrder.nativeOrder());
    return map;
  }

  private void remap() {
    try {
      if (this.mappedBuffer != null) {
        unsafeUnmap(this.mappedBuffer);
        this.flush();
      }
      this.mappedBuffer = this.mmap(this.randomAccessFile.getChannel(), 0, DEFAULT_REGION_LENGTH);
    } catch (final Exception ex) {
      System.out.println("Unable to remap" + ex.getMessage());
    }
  }

  private static void unsafeUnmap(final MappedByteBuffer mbb) throws Exception {
/*     final Method getCleanerMethod = mbb.getClass().getMethod("cleaner");
     getCleanerMethod.setAccessible(true);
     final Object cleaner = getCleanerMethod.invoke(mbb); // sun.misc.Cleaner instance
     final Method cleanMethod = cleaner.getClass().getMethod("clean");
    cleanMethod.invoke(cleaner);*/
  }

  public final void flush() {
    super.flush();
    this.mappedBuffer.force();
  }

  protected void write(final byte[] bytes, int offset, int length) throws Exception {
    while (length > this.mappedBuffer.remaining()) {
      final int chunk = this.mappedBuffer.remaining();
      this.mappedBuffer.put(bytes, offset, chunk);
      offset += chunk;
      length -= chunk;
      this.remap();
    }
    this.mappedBuffer.put(bytes, offset, length);
  }
}
