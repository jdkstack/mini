package org.jdkstack.jdkbean.core.bean;

import org.jdkstack.jdkbean.api.bean.Bean;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class BeanService implements Bean {

  /** . */
  private boolean singleton;
  /** . */
  private Object obj;
  /** . */
  private Class<?> classObj;
  /** . */
  private String name;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public BeanService() {
    //
  }

  @Override
  public final boolean isSingleton() {
    return this.singleton;
  }

  @Override
  public final void setSingleton(final boolean singleton) {
    this.singleton = singleton;
  }

  @Override
  public final Object getObj() {
    return this.obj;
  }

  @Override
  public final void setObj(final Object obj) {
    this.obj = obj;
  }

  @Override
  public final Class<?> getClassObj() {
    return this.classObj;
  }

  @Override
  public final void setClassObj(final Class<?> classObj) {
    this.classObj = classObj;
  }

  @Override
  public final String getName() {
    return this.name;
  }

  @Override
  public final void setName(final String name) {
    this.name = name;
  }
}
