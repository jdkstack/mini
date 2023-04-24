package org.jdkstack.logging.mini.api.resource;

/**
 * .
 *
 * <p>。
 *
 * @param <T> t .
 * @author admin
 */
public interface Factory<T> {

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @return boolean .
   * @author admin
   */
  boolean contains(String key);

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param t .
   * @author admin
   */
  void add(String key, T t);

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @author admin
   */
  void delete(String key);

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param t .
   * @author admin
   */
  void update(String key, T t);

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @return String .
   * @author admin
   */
  String query(String key);

  /**
   * .
   *
   * <p>.
   *
   * @return String .
   * @author admin
   */
  String all();
}
