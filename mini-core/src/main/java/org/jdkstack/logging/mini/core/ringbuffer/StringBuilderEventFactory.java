package org.jdkstack.logging.mini.core.ringbuffer;

import org.jdkstack.ringbuffer.core.EventFactory;

public class StringBuilderEventFactory<E> implements EventFactory<E> {

  @Override
  public final E newInstance() {
    return (E) new StringBuilder(2048);
  }
}

