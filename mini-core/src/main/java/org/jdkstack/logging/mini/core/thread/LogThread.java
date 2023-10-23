package org.jdkstack.logging.mini.core.thread;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.Encoder;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.handler.Constants;

/**
 * 自定义线程,便于系统内线程的监控.
 *
 * <p>比如设置自定义的线程名,线程计数等.
 *
 * @author admin
 */
public final class LogThread extends Thread {

  /** 线程开始运行的时间(毫秒). */
  private long execStart;

  /** 按照文件大小切割. */
  private final AtomicInteger sizes = new AtomicInteger(0);

  /** 按照文件条数切割. */
  private final AtomicInteger lines = new AtomicInteger(0);

  /** 临时数组. */
  private final CharBuffer charBuf = CharBuffer.allocate(Constants.SOURCE);

  private final StringBuilder sb = new StringBuilder(20480);

  public StringBuilder getSb() {
    return this.sb;
  }

  /** 字符编码器. */
  private final Encoder<CharBuffer> textEncoder = new CharArrayEncoderV2(Charset.defaultCharset());

  /** 目的地写入器. */
  private final ByteWriter destination = new ByteArrayWriter();

  /** . */
  private RingBuffer<File> buffer;

  /** . */
  private RingBuffer<RandomAccessFile> rabuffer;

  /** . */
  private RandomAccessFile randomAccessFile;

  /** . */
  private FileChannel channel;

  /**
   * 自定义线程.
   *
   * <p>参数需要加final修饰.
   *
   * @param targetParam 线程任务.
   * @param nameParam 线程名.
   * @author admin
   */
  public LogThread(final Runnable targetParam, final String nameParam) {
    super(targetParam, nameParam);
  }

  public RingBuffer<File> getBuffer() {
    return this.buffer;
  }

  public void setBuffer(final RingBuffer<File> buffer) {
    this.buffer = buffer;
  }

  public RingBuffer<RandomAccessFile> getRabuffer() {
    return this.rabuffer;
  }

  public void setRabuffer(final RingBuffer<RandomAccessFile> rabuffer) {
    this.rabuffer = rabuffer;
  }

  public AtomicInteger getSizes() {
    return this.sizes;
  }

  public AtomicInteger getLines() {
    return this.lines;
  }

  public CharBuffer getCharBuf() {
    return this.charBuf;
  }

  public Encoder<CharBuffer> getTextEncoder() {
    return this.textEncoder;
  }

  public ByteWriter getDestination() {
    return this.destination;
  }

  public RandomAccessFile getRandomAccessFile() {
    return this.randomAccessFile;
  }

  public void setRandomAccessFile(final RandomAccessFile randomAccessFile) {
    this.randomAccessFile = randomAccessFile;
  }

  public FileChannel getChannel() {
    return this.channel;
  }

  public void setChannel(final FileChannel channel) {
    this.channel = channel;
  }

  /**
   * 获取线程运行的开始时间.
   *
   * @return 返回线程的开始运行时间.
   * @author admin
   */
  public long startTime() {
    return this.execStart;
  }

  /**
   * 当线程开始时,开始时间设置成当前系统的时间戳毫秒数.
   *
   * @author admin
   */
  private void executeStart() {
    // 设置当前系统时间为开始时间,代表线程开始执行.
    this.execStart = System.currentTimeMillis();
  }

  private void executeEnd() {
    // 设置当前系统时间为0,代表线程执行完毕.
    this.execStart = 0;
  }

  /**
   * 给线程设置一个上下文环境对象.
   *
   * <p>代表线程正在运行着.
   *
   * @author admin
   */
  public void beginEmission() {
    // 设置执行开始时间.
    this.executeStart();
  }

  /**
   * 将线程上下文环境对象设置为空.
   *
   * <p>代表线程运行完毕.
   *
   * @author admin
   */
  public void endEmission() {
    // 设置执行结束时间.
    this.executeEnd();
  }
}
