package org.jdkstack.asynchronous.api.worker;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <E> the parameter of the class.
 * @author admin
 */
public interface Worker<E> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param event 任意对象.
   * @author admin
   */
  void handle(E event);
}
