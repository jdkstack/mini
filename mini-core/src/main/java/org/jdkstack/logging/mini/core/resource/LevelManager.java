package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.resource.LeFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class LevelManager {

  /** . */
  private final LeFactory leFactory;

  /**
   * .
   *
   * <p>.
   *
   * @param leFactory .
   * @author admin
   */
  @ConstructorResource("levelFactory")
  public LevelManager(final LeFactory leFactory) {
    this.leFactory = leFactory;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param name .
   * @param value .
   * @author admin
   */
  public final void create(final String name, final int value) {
    this.leFactory.putLevel(name, value);
  }
}
