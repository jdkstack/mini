package org.jdkstack.logging.mini.core.handler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.logging.mini.api.handler.Handler;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {

  /** 锁. */
  protected final Lock lock = new ReentrantLock();

  /**
   * 打开流.
   *
   * @author admin
   */
  public abstract void doOpen();

  /**
   * 关闭流.
   *
   * @author admin
   */
  public abstract void doClose();
}
