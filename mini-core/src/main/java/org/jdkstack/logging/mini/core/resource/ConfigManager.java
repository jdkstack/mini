package org.jdkstack.logging.mini.core.resource;

import java.util.Map;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.resource.CoFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class ConfigManager {
  /** . */
  private final CoFactory configFactory;

  /**
   * .
   *
   * <p>.
   *
   * @param configFactory configFactory.
   * @author admin
   */
  @ConstructorResource("configFactory")
  public ConfigManager(final CoFactory configFactory) {
    this.configFactory = configFactory;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param name .
   * @param map .
   * @author admin
   */
  public final void create(final String name, final Map<String, String> map) {
    this.configFactory.put(name, map);
  }
}
