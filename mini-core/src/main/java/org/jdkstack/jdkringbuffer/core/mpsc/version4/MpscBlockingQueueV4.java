package org.jdkstack.jdkringbuffer.core.mpsc.version4;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV4;
import org.jdkstack.jdkringbuffer.core.Entry;
import org.jdkstack.jdkringbuffer.core.EventFactory;
import org.jdkstack.jdkringbuffer.core.Power2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <E> e.
 * @author admin
 */
public class MpscBlockingQueueV4<E> extends AbstractLockBlockingQueueV4<E> {

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param eventFactory .
   * @author admin
   */
  public MpscBlockingQueueV4(final EventFactory<E> eventFactory) {
    super(eventFactory);
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity     .
   * @param eventFactory .
   * @author admin
   */
  public MpscBlockingQueueV4(final int capacity, final EventFactory<E> eventFactory) {
    super(Power2.power2(capacity), eventFactory);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean e.
   * @author admin
   */
  @Override
  public final E poll() {
    final int headSeq = this.head.get();
    final Entry<E> cell = this.buffer[headSeq & this.index];
    final int seq = cell.getSeq();
    final int dif = seq - (headSeq + 1);
    E e = null;
    if (0 == dif) {
      e = cell.getEntry();
      cell.setSeq(headSeq + this.index + 1);
      this.head.getAndIncrement();
    }
    return e;
  }
}
