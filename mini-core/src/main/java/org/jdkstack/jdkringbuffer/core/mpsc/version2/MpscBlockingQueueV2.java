package org.jdkstack.jdkringbuffer.core.mpsc.version2;

import org.jdkstack.jdkringbuffer.core.AbstractLockBlockingQueueV2;
import org.jdkstack.jdkringbuffer.core.Constants;
import org.jdkstack.jdkringbuffer.core.Entry;
import org.jdkstack.jdkringbuffer.core.Power2;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 * @param <E> e.
 */
public class MpscBlockingQueueV2<E> extends AbstractLockBlockingQueueV2<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public MpscBlockingQueueV2() {
    super(Constants.CAPACITY);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param capacity e.
   */
  public MpscBlockingQueueV2(final int capacity) {
    super(Power2.power2(capacity));
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @return boolean e.
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
      cell.setEntry(null);
      cell.setSeq(headSeq + this.index + 1);
      this.head.getAndIncrement();
    }
    return e;
  }
}
