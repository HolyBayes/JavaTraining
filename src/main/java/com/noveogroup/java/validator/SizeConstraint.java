package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Vector;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Size constraint class exists @Size(min,max) annotation (default value "-1" means infinite)
 * and @Size validator
 * @author artem ryzhikov
 */
public class SizeConstraint implements Validator {

    @Target({ FIELD , METHOD , ANNOTATION_TYPE , CONSTRUCTOR , PARAMETER })
    @Retention(RUNTIME)
    public @interface Size {
        int min() default -1;
        int max() default -1;
    }
    @Override
    public void validate(final Object obj) throws ValidatorException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(Size.class)) {
                final int min = f.getAnnotation(Size.class).min();
                final int max = f.getAnnotation(Size.class).max();
                validate(f , obj , min , max);
            }
        }
    }
    public void validate(final Field f ,
                         final Object obj ,
                         final int min ,
                         final int max) throws ValidatorException {
        try {
            f.setAccessible(true);
            boolean flag = false;
            final String message = "@Size constraint in " + obj.getClass().getName();
            if (f.getType().equals(java.lang.String.class)) {
                final String s = (String) f.get(obj);
                if (((s.length() > max)
                        &&
                        (max != -1))
                        ||
                        ((s.length() < min))
                            &&
                                (min != -1)) {
                    throw new ValidatorException(message);
                }
                flag = true;
            }
            if (f.isEnumConstant()) {
                final Vector v = (Vector) f.get(obj);
                if (((v.size() > max)
                        &&
                        (max != -1))
                        ||
                        ((v.size() < min))
                            &&
                                (min != -1)) {
                    throw new ValidatorException(message);
                }
                flag = true;
            }
            if (!flag) {
                throw new IllegalStateException("");
            }
        } catch (IllegalAccessException e) {
            System.out.print("Illegal Exception was thrown in " + obj.getClass().getName());
        }
    }
}
