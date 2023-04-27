package org.jdkstack.logging.mini.api.resource;

import java.util.Map;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public interface CoFactory {

  /**
   * .
   *
   * <p>.
   *
   * @param name name .
   * @param value value .
   * @author admin
   */
  void put(String name, Map<String, String> value);

  /**
   * .
   *
   * <p>.
   *
   * @param name name .
   * @return Map .
   * @author admin
   */
  Map<String, String> get(String name);

  /**
   * .
   *
   * <p>.
   *
   * @param name name .
   * @param key key .
   * @return String .
   * @author admin
   */
  String getValue(String key, String name);
  //
}
