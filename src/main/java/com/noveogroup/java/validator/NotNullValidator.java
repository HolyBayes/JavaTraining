package com.noveogroup.java.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by artem on 15.08.14.
 */
public class NotNullValidator implements Validator {
    //annotation
    private final NotNull annotation;
    public NotNullValidator(final Annotation annotation) {
        this.annotation = (NotNull) annotation;
    }
    public void validate(final Object object , final Field field) throws ValidateException , IllegalAccessException {
        if (object == null) {
            field.setAccessible(true);
            throw new ValidateException ("@NotNull constraint" , field.getName() , field.get(object));
        }

    }
}

