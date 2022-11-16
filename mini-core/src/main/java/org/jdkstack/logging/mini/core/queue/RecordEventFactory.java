package org.jdkstack.logging.mini.core.queue;

import org.jdkstack.jdkringbuffer.core.EventFactory;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.record.LogRecord;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class RecordEventFactory implements EventFactory<Record> {

  @Override
  public final Record newInstance() {
    return new LogRecord();
  }
}
