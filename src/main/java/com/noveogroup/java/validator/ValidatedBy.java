package com.noveogroup.java.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by artem on 15.08.14.
 */
@Retention(RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValidatedBy {
    Class<? extends Validator> value();
}
