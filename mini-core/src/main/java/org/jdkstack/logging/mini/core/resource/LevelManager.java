package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.resource.LeFactory;
import org.jdkstack.logging.mini.core.level.Constants;

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
    this.init();
  }

  private void init() {
    this.create(Constants.MIN, Constants.MIN_VALUE);
    this.create(Constants.SEVERE, Constants.SEVERE_VALUE);
    this.create(Constants.FATAL, Constants.FATAL_VALUE);
    this.create(Constants.ERROR, Constants.ERROR_VALUE);
    this.create(Constants.WARN, Constants.WARN_VALUE);
    this.create(Constants.INFO, Constants.INFO_VALUE);
    this.create(Constants.DEBUG, Constants.DEBUG_VALUE);
    this.create(Constants.CONFIG, Constants.CONFIG_VALUE);
    this.create(Constants.FINE, Constants.FINE_VALUE);
    this.create(Constants.FINER, Constants.FINER_VALUE);
    this.create(Constants.FINEST, Constants.FINEST_VALUE);
    this.create(Constants.TRACE, Constants.TRACE_VALUE);
    this.create(Constants.MAX, Constants.MAX_VALUE);
  }

  /**
   * .
   *
   * <p>.
   *
   * @param name  .
   * @param value .
   * @author admin
   */
  public final void create(final String name, final int value) {
    this.leFactory.putLevel(name, value);
  }
}
