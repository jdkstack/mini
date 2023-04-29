package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.resource.FilFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class FilterManager {

  /** . */
  private final FilFactory<? super Filter> filterFactory;

  /**
   * .
   *
   * <p>.
   *
   * @param filterFactory .
   * @author admin
   */
  @ConstructorResource("filterFactory")
  public FilterManager(final FilFactory<? super Filter> filterFactory) {
    this.filterFactory = filterFactory;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param filter .
   * @author admin
   */
  public final void create(final String key, final Filter filter) {
    this.filterFactory.add(key, filter);
  }
}
