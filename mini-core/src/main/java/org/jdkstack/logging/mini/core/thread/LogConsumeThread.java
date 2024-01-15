package org.jdkstack.logging.mini.core.thread;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.jdkstack.logging.mini.api.buffer.ByteWriter;
import org.jdkstack.logging.mini.api.codec.CodecEncoder;
import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.lz4.Encoder;
import org.jdkstack.logging.mini.api.ringbuffer.RingBuffer;
import org.jdkstack.logging.mini.core.algo.lz4.Lz4Encoder;
import org.jdkstack.logging.mini.core.buffer.ByteArrayWriter;
import org.jdkstack.logging.mini.core.buffer.MmapByteArrayWriter;
import org.jdkstack.logging.mini.core.codec.CharArrayEncoderV2;
import org.jdkstack.logging.mini.core.codec.Constants;
import org.jdkstack.logging.mini.core.ringbuffer.FileRingBuffer;
import org.jdkstack.logging.mini.core.ringbuffer.RandomAccessFileRingBuffer;

/**
 * 自定义线程,便于系统内线程的监控.
 *
 * <p>比如设置自定义的线程名,线程计数等.
 *
 * @author admin
 */
public final class LogConsumeThread extends Thread {


  /**
   * 按照文件大小切割.
   */
  private final AtomicInteger sizes = new AtomicInteger(0);

  /**
   * 按照文件条数切割.
   */
  private final AtomicInteger lines = new AtomicInteger(0);

  /**
   * 目的地写入器.
   */
  private final ByteWriter destination = new ByteArrayWriter();
  /**
   * 临时数组.
   */
  private final CharBuffer TEXT_CHARBUF = CharBuffer.allocate(Constants.SOURCEN8);
  /**
   * 临时数组.
   */
  private final CharBuffer JSON_CHARBUF = CharBuffer.allocate(Constants.SOURCEN8);

  private final StringBuilder text = new StringBuilder(Constants.SOURCEN8);

  private final StringBuilder json = new StringBuilder(Constants.SOURCEN8);

  /**
   * 目的地写入器.
   */
  private final ByteWriter mmapByteArrayWriter = new MmapByteArrayWriter();
  /**
   * 临时数组.
   */
  private final CharBuffer charBuf = CharBuffer.allocate(org.jdkstack.logging.mini.core.handler.Constants.SOURCE);
  /**
   * 字符编码器.
   */
  private final CodecEncoder<CharBuffer> textCodecEncoder = new CharArrayEncoderV2(Charset.defaultCharset());
  /**
   * 配置.
   */
  private HandlerConfig rc;
  /**
   * 目录.
   */
  private File dir;
  /**
   * .
   */
  private RingBuffer<File> fileBuffer;
  /**
   * .
   */
  private RingBuffer<RandomAccessFile> randomAccessFileBuffer;
  /**
   * .
   */
  private RandomAccessFile randomAccessFile;
  /**
   * .
   */
  private MappedByteBuffer mappedBuffer;
  /**
   * .
   */
  private FileChannel channel;
  private ByteWriter destination3;
  /**
   * 共享锁.
   */
  FileLock lock;

  /**
   * 线程开始运行的时间(毫秒).
   */
  private long execStart;
  public final Encoder lz4Encoder = new Lz4Encoder();
  public final ByteBuffer out = ByteBuffer.allocate(2048);
  public final ByteBuffer out2 = ByteBuffer.allocate(2048);

  public Encoder getEncoderLz4() {
    return this.lz4Encoder;
  }

  public ByteBuffer getOut() {
    return this.out;
  }

  public ByteBuffer getOut2() {
    return this.out2;
  }

  public FileLock getLock() {
    return this.lock;
  }

  public void setLock(final FileLock lock) {
    this.lock = lock;
  }

  /**
   * 自定义线程.
   *
   * <p>参数需要加final修饰.
   *
   * @param targetParam 线程任务.
   * @param nameParam   线程名.
   * @author admin
   */
  public LogConsumeThread(final Runnable targetParam, final String nameParam) {
    super(targetParam, nameParam);
  }

  public StringBuilder getText() {
    return this.text;
  }

  public StringBuilder getJson() {
    return this.json;
  }

  public ByteWriter getDestination3() {
    return this.destination3;
  }

  public void setDestination3(ByteWriter destination3) {
    this.destination3 = destination3;
  }

  public CharBuffer getCharBuf() {
    return this.charBuf;
  }

  public CodecEncoder<CharBuffer> getTextEncoder() {
    return this.textCodecEncoder;
  }

  public ByteWriter getMmapByteArrayWriter() {
    return this.mmapByteArrayWriter;
  }

  public AtomicInteger getSizes() {
    return this.sizes;
  }

  public void setSizes(int i) {
    this.sizes.set(i);
  }

  public AtomicInteger getLines() {
    return this.lines;
  }

  public void setLines(int i) {
    this.lines.set(i);
  }

  public ByteWriter getDestination() {
    return this.destination;
  }

  public MappedByteBuffer getMappedBuffer() {
    return this.mappedBuffer;
  }

  public void setMappedBuffer(final MappedByteBuffer mappedBuffer) {
    this.mappedBuffer = mappedBuffer;
  }

  public FileChannel getChannel() {
    return this.channel;
  }

  public void setChannel(final FileChannel channel) {
    this.channel = channel;
  }

  public HandlerConfig getRc() {
    return this.rc;
  }

  public void setRc(final HandlerConfig rc) {
    this.rc = rc;
    dir = new File(this.rc.getDirectory() + File.separator + this.rc.getPrefix() + File.separator + Thread.currentThread().getName());
    fileBuffer = new FileRingBuffer(this.dir, this.rc);
    randomAccessFileBuffer = new RandomAccessFileRingBuffer(this.fileBuffer, this.rc);
  }

  public File getDir() {
    return this.dir;
  }

  public RingBuffer<File> getFileBuffer() {
    return this.fileBuffer;
  }

  public RingBuffer<RandomAccessFile> getRandomAccessFileBuffer() {
    return this.randomAccessFileBuffer;
  }

  public RandomAccessFile getRandomAccessFile() {
    return this.randomAccessFile;
  }

  public void setRandomAccessFile(final RandomAccessFile randomAccessFile) {
    this.randomAccessFile = randomAccessFile;
  }

  public CharBuffer getTEXT_CHARBUF() {
    return this.TEXT_CHARBUF;
  }

  public CharBuffer getJSON_CHARBUF() {
    return this.JSON_CHARBUF;
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
