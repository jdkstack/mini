package org.jdkstack.logging.mini.core.record;

import org.jdkstack.logging.mini.api.ringbuffer.EventFactory;

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
