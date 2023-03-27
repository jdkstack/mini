package org.jdkstack.logging.mini.core.queue;

import org.jdkstack.logging.mini.core.record.LogRecord;
import org.jdkstack.ringbuffer.core.EventFactory;

/**
 * .
 *
 * <p>.
 *
 * @param <E> .
 * @author admin
 */
public class RecordEventFactory<E> implements EventFactory<E> {

  @Override
  public final E newInstance() {
    return (E) new LogRecord();
  }
}
