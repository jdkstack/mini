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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodResource {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String value() default "";
}
