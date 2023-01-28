package org.jdkstack.asynchronous.core;

import org.jdkstack.jdkringbuffer.core.EventFactory;

/**
 * .
 *
 * <p>.
 *
 * @param <E> .
 * @author admin
 */
public class TaskWorkEventFactory<E> implements EventFactory<E> {

  @Override
  public final E newInstance() {
    return (E) new TaskWorker();
  }
}
