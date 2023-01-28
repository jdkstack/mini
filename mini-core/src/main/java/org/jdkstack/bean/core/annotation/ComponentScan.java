package org.jdkstack.bean.core.annotation;

import java.lang.annotation.ElementType;
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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScan {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  String[] value() default {};

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  Filter[] includeFilters() default {};

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  Filter[] excludeFilters() default {};
}
