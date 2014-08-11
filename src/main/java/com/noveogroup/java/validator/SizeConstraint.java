package com.noveogroup.java.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Size constraint class exists @Size(min,max) annotation (default value "-1" means infinite)
 * and @Size validator
 * @author artem ryzhikov
 */
public class SizeConstraint implements Validator {
    public final static int INFINITE =-1;
    private final static Logger LOG = Logger.getLogger(SizeConstraint.class.getName());

    @Target({ FIELD , METHOD , ANNOTATION_TYPE , CONSTRUCTOR , PARAMETER })
    @Retention(RUNTIME)
    public @interface Size {
        int min() default 0;
        int max() default INFINITE;
    }
    @Override
    public void validate(final Object obj) throws ValidateException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(Size.class)) {
                final int min = f.getAnnotation(Size.class).min();
                final int max = f.getAnnotation(Size.class).max();
                validate(f , obj , min , max);
            }
        }
    }
    private boolean compare(int value , int min , int max) {
        if(value > min && (value < max || max == INFINITE )){
            return true;
        }
        return false;
    }
    public void validate(final Field f ,
                         final Object obj ,
                         final int min ,
                         final int max) throws ValidateException {
        try {
            f.setAccessible(true);
            boolean flag = false;
            String value = new String();
            try {
                value = f.get(obj).toString();
            } catch (IllegalAccessException e) {
                LOG.log(Level.FINE, "IllegalAccessException in Object" +
                        obj.getClass().getName() +
                        "\n in field" +
                        f.getName());
            }
            final String MESSAGE = "@Size constraint in " + obj.getClass().getName() +
                    "\n in Field: " +
                    f.getName() +
                    "\n with value: " +
                    value;
            if (f.getType().equals(java.lang.String.class)) {
                final String s = (String) f.get(obj);
                if (!compare(s.length() , min , max)) {
                    throw new ValidateException(MESSAGE);
                }
                flag = true;
            }
            if (f.isEnumConstant()) {
                final Vector v = (Vector) f.get(obj);
                if (!compare(v.size() , min , max)) {
                    throw new ValidateException(MESSAGE);
                }
                flag = true;
            }
            if (!flag) {

                throw new IllegalStateException("Illegal Exception was thrown in " +
                        obj.getClass().getName()
                        );
            }
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "IllegalAccessException :", e);
        }
    }
}
