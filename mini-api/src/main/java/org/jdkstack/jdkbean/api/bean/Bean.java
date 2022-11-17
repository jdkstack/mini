package org.jdkstack.jdkbean.api.bean;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Bean {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return b .
   * @author admin
   */
  boolean isSingleton();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param singleton .
   * @author admin
   */
  void setSingleton(boolean singleton);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  Object getObj();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param obj .
   * @author admin
   */
  void setObj(Object obj);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  Class<?> getClassObj();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param classObj .
   * @author admin
   */
  void setClassObj(Class<?> classObj);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  String getName();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  void setName(String name);
}
