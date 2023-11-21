package org.jdkstack.logging.mini.extension.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Http 请求头
 *
 * @author admin
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHeader {

  String value() default "";

  boolean required() default true;
}
