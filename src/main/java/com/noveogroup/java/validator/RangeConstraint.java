package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * -//- (see NotBlankConstraint)
 */
public class RangeConstraint implements Validator {
    @Target({ FIELD , METHOD , ANNOTATION_TYPE , CONSTRUCTOR , PARAMETER })
    @Retention(RUNTIME)
    public @interface Range {
            int min();
            int max();
        }
    @Override
        public void validate(final Object obj) throws ValidatorException {
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f:fields) {
                if (f.isAnnotationPresent(Range.class)) {
                    final int min = f.getAnnotation(Range.class).min();
                    final int max = f.getAnnotation(Range.class).max();
                    validate(f , obj , min , max);
                }
            }
        }
    public void validate(final Field f , final Object obj , final int min , final int max) throws ValidatorException {
        try {
            f.setAccessible(true);
            if (!(f.isEnumConstant())) {
                throw new IllegalAccessException();
            }
            if (((Integer) f.get(obj) > max)
                    ||
                    ((Integer) f.get(obj) < min)) {
                throw new ValidatorException("@Range constraint in " + obj.getClass().getName());
            }
        } catch (IllegalAccessException e) {
            System.out.print("Illegal Exception was thrown in " + obj.getClass().getName());
        }
    }
}
