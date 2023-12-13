package org.jdkstack.logging.mini.core.ringbuffer;

import com.lmax.disruptor.WorkHandler;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.record.Record;

public class RingBufferLogWorkHandler implements WorkHandler<Record> {

  private LogRecorderContext context;

  public RingBufferLogWorkHandler(LogRecorderContext context) {
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
