package org.jdkstack.logging.mini.core.resource;

import org.jdkstack.bean.core.annotation.Component;
import org.jdkstack.bean.core.annotation.ConstructorResource;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.resource.ForFactory;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
@Component
public class FormatterManager {

  /** . */
  private final ForFactory<? super Formatter> formatterFactory;

  /**
   * .
   *
   * <p>.
   *
   * @param formatterFactory .
   * @author admin
   */
  @ConstructorResource("formatterFactory")
  public FormatterManager(final ForFactory<? super Formatter> formatterFactory) {
    this.formatterFactory = formatterFactory;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param key .
   * @param formatter .
   * @author admin
   */
  public final void create(final String key, final Formatter formatter) {
    this.formatterFactory.add(key, formatter);
  }
}
