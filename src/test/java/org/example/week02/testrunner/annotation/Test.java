package org.example.week02.testrunner.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // В .class файле и доступна через рефлексию
public @interface Test {
  String description() default "";
}
