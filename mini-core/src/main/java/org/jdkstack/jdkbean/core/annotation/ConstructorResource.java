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
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstructorResource {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return String[] .
   * @author admin
   */
  String[] value() default {};
}
