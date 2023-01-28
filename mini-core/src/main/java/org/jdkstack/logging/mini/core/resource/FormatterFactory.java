package org.jdkstack.logging.mini.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.ForFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class FormatterFactory implements ForFactory<Formatter> {

  /** . */
  private final Map<String, Formatter> formatters = new ConcurrentHashMap<>(32);

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
  public final void add(final String key, final Formatter t) {
    this.formatters.put(key, t);
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
    //
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
  public void update(final String key, final Formatter t) {
    //
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
   * @param formatterName formatterName.
   * @param logRecord     logRecord.
   * @return StringBuilder .
   * @author admin
   */
  @Override
  public final StringBuilder formatter(final String formatterName, final Record logRecord) {
    final Formatter formatter = this.formatters.get(formatterName);
    return formatter.format(logRecord);
  }
}
