package org.jdkstack.jdkbean.core.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.jdkbean.api.bean.Bean;
import org.jdkstack.jdkbean.api.factory.Factory;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class BeanFactory implements Factory {

  /** . */
  private final Map<String, Bean> beansMap = new ConcurrentHashMap<>(256);

  @Override
  public final void addBean(final String name, final Bean bean) {
    this.beansMap.put(name, bean);
  }

  @Override
  public final Bean getBean(final String name) {
    return this.beansMap.get(name);
  }

  @Override
  public final Object getObject(final String name) {
    return this.beansMap.get(name).getObj();
  }
}
