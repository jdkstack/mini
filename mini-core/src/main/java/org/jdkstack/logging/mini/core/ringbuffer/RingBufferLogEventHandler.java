package org.jdkstack.logging.mini.core.ringbuffer;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.context.AsyncLogRecorderContext;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class RingBufferLogEventHandler
    implements SequenceReportingEventHandler<Record>, LifecycleAware {
  private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
  private Sequence sequenceCallback;
  private int counter;
  private long threadId = -1;

  private AsyncLogRecorderContext context;

  public RingBufferLogEventHandler(AsyncLogRecorderContext context) {
    this.context = context;
  }

  public long getThreadId() {
    return threadId;
  }

  @Override
  public void onStart() {
    threadId = Thread.currentThread().getId();
  }

  @Override
  public void onShutdown() {
    //
  }

  @Override
  public void setSequenceCallback(Sequence sequenceCallback) {
    this.sequenceCallback = sequenceCallback;
  }

  @Override
  public void onEvent(Record event, long sequence, boolean endOfBatch) throws Exception {
    try {
      // 调用真正的消费逻辑。
      context.consume(event);
    } finally {
      event.clear();
      notifyCallback(sequence);
    }
  }

  private void notifyCallback(long sequence) {
    if (++counter > NOTIFY_PROGRESS_THRESHOLD) {
      sequenceCallback.set(sequence);
      counter = 0;
    }
  }
}
