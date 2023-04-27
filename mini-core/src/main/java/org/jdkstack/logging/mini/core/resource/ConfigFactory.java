package org.jdkstack.logging.mini.core.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.logging.mini.api.resource.CoFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class ConfigFactory implements CoFactory {
  /** . */
  private final Map<String, Map<String, String>> configs = new ConcurrentHashMap<>(32);

  @Override
  public final void put(final String name, final Map<String, String> value) {
    this.configs.putIfAbsent(name, value);
  }

  @Override
  public final Map<String, String> get(final String name) {
    return this.configs.get(name);
  }

  @Override
  public final String getValue(final String key, final String name) {
    final Map<String, String> map = this.configs.get(key);
    return map.get(name);
  }
}
