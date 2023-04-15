package org.jdkstack.logging.mini.core.ringbuffer;

import org.jdkstack.ringbuffer.core.EventFactory;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @param <E> e.
 * @author admin
 */
public class StringBuilderEventFactory<E> implements EventFactory<E> {

  @Override
  public final E newInstance() {
    return (E) new StringBuilder(Constants.CAPACITY);
  }
}

