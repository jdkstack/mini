package org.jdkstack.logging.mini.extension.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {

  Class<?>[] value() default {};
}
