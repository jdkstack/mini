package org.jdkstack.logging.mini.core.record;

import com.lmax.disruptor.EventFactory;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * .
 *
 * <p>创建日志记录对象.
 *
 * @param <E> .
 * @author admin
 */
public class RecordEventFactory implements EventFactory<Record> {

  @Override
  public final Record newInstance() {
    return new LogRecord();
  }
}
