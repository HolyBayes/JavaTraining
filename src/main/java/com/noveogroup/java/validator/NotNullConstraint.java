package com.noveogroup.java.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * NotNullConstraint class consist of -//- (see NotBlankConstraint)
 * @author artem ryzhikov
 */
    public class NotNullConstraint implements Validator {
        @Target(value = { METHOD , FIELD , ANNOTATION_TYPE , CONSTRUCTOR , PARAMETER })
        @Retention(RUNTIME)
        public @interface NotNull {
        }
        @Override
        public void validate(final Object obj) throws ValidateException {
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f:fields) {
                if (f.isAnnotationPresent(NotNull.class)) {
                    validate(f , obj);
                }
            }
        }
        public void validate(final Field f , final Object obj) throws ValidateException {
            try {
                f.setAccessible(true);
                if (f.get(obj) == null) {
                    throw new ValidateException("@NotNull constraint in " + obj.getClass().getName());
                }
            } catch (IllegalAccessException e) {
                System.out.print("Illegal Exception was thrown in " + obj.getClass().getName());
            }
        }
    }