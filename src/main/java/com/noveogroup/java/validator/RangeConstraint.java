package com.noveogroup.java.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * -//- (see NotBlankConstraint)
 * @author artem ryzhikov
 */

public class RangeConstraint implements Validator {
    private static Logger log = Logger.getLogger(RangeConstraint.class.getName());

    @Target({ FIELD , METHOD , ANNOTATION_TYPE , CONSTRUCTOR , PARAMETER })
    @Retention(RUNTIME)
    public @interface Range {
        int min();
        int max();
    }
    @Override
    public void validate(final Object obj) throws ValidateException {
        final Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(Range.class)) {
                final int min = f.getAnnotation(Range.class).min();
                final int max = f.getAnnotation(Range.class).max();
                validate(f , obj , min , max);
            }
        }
    }
    public void validate(final Field f , final Object obj , final int min , final int max)
            throws ValidateException {
        try {
            f.setAccessible(true);

            if (!(f.isEnumConstant())) {
                throw new IllegalAccessException();
            }
            if (((Integer) f.get(obj) > max)
                    ||
                    ((Integer) f.get(obj) < min)) {
                String value = new String();
                try {
                    value = f.get(obj).toString();
                } catch (IllegalAccessException e) {
                    log.log(Level.FINE , "IllegalAccessException in Object" +
                            obj.getClass().getName() +
                            "\n in field" +
                            f.getName());
                }
                throw new ValidateException("@Range constraint in " +
                        obj.getClass().getName() +
                        "\n in Field: " +
                        f.getName() +
                        "\n with value: " +
                        value);
            }
        } catch (IllegalAccessException e) {
            log.log(Level.SEVERE , "IllegalAccessException was thrown :" , e);
        }
    }
}
