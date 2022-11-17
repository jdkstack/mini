package org.jdkstack.jdkbean.core.annotation;

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
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldResource {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return E .
   * @author admin
   */
  String value() default "";
}
