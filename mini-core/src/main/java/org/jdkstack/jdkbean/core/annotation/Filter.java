package org.jdkstack.jdkbean.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  Class<?>[] value() default {};
}
