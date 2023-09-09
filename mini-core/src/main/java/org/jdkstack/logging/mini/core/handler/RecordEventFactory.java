package org.jdkstack.logging.mini.core.handler;

import org.jdkstack.logging.mini.core.record.LogRecord;
import org.jdkstack.ringbuffer.core.EventFactory;

/**
 * .
 *
 * <p>创建日志记录对象.
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
