package org.jdkstack.bean.api.context;

import org.jdkstack.bean.api.bean.Bean;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Context {

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param application .
   * @author admin
   */
  void scan(Class<?> application);

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @param bean .
   * @author admin
   */
  void addBean(String name, Bean bean);

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return E .
   * @author admin
   */
  Bean getBean(String name);

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return E .
   * @author admin
   */
  Object getObject(String name);
}
