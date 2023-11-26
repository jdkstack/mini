package org.jdkstack.logging.mini.core.ringbuffer;

import com.lmax.disruptor.WorkHandler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.context.AsyncLogRecorderContext;

public class RingBufferLogWorkHandler implements WorkHandler<Record> {

  private AsyncLogRecorderContext context;

  public RingBufferLogWorkHandler(AsyncLogRecorderContext context) {
    this.context = context;
  }

  @Override
  public void onEvent(Record event) throws Exception {
    try {
      // 调用真正的消费逻辑。
      context.consume(event);
    } finally {
      event.clear();
    }
  }
}