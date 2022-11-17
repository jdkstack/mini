package org.jdkstack.logging.mini.core.queue;

import org.jdkstack.jdkringbuffer.core.EventFactory;
import org.jdkstack.logging.mini.core.record.LogRecord;

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
