package com.noveogroup.java.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by artem on 16.08.14.
 */
@ValidatedBy(PatternValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@NotNull
@Size(min = 1)
public @interface Pattern {
    String regexp();
}
