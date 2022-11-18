package org.jdkstack.logging.mini.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.jdkbean.core.annotation.Component;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.FilFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class FilterFactory implements FilFactory<Filter> {

  /** . */
  private final Map<String, Filter> filters = new ConcurrentHashMap<>(32);

  /**
   * .
   *
   * <p>.
   *
   * @author admin
   */
  public FilterFactory() {
    //
  }

  /**
   * .
   *
   * <p>.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final boolean contains(final String key) {
    return false;
  }


  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param t   .
   * @author admin
   */
  @Override
  public final void add(final String key, final Filter t) {
    this.filters.put(key, t);
  }


  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @author admin
   */
  @Override
  public void delete(final String key) {
    this.filters.remove(key);
  }

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param t   .
   * @author admin
   */
  @Override
  public void update(final String key, final Filter t) {
    this.filters.put(key, t);
  }

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @return String .
   * @author admin
   */
  @Override
  public final String query(final String key) {
    return null;
  }

  /**
   * .
   *
   * <p>.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String all() {
    return null;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param logRecord  logRecord.
   * @param filterName filterName.
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean filter(final String filterName, final Record logRecord) {
    final Filter filter = this.filters.get(filterName);
    return filter.doFilter(logRecord);
  }
}
