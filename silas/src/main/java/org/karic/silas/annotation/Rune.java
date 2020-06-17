package org.karic.silas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rune {
    String id();

    int sequence() default -1;

    String childMatchedId();

    String childUnmatchId();

    String childExceptionId();

    String group();

    int groupSequence() default -1;
}
