package com.noveogroup.java.validator;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Size constraint class exists @Size(min,max) annotation (default value "-1" means infinite).
 * and @Size validator
 * @author artem ryzhikov
 */
public class SizeValidator implements Validator {
    public static final int INFINITE = -1;
    private static final Logger LOG = Logger.getLogger(SizeValidator.class.getName());
    private final Size annotation;

    public SizeValidator(final Annotation annotation) {
        this.annotation = (Size) annotation;
    }

    private boolean compare(final int value , final int min , final int max) {
        if (value < min || (value > max && max != INFINITE)) {
            return false;
        }
        return true;
    }

    @Override
    public void validate(final Object obj ,
                         final Field field
    ) throws ValidateException {

        try {
            final int min = annotation.min();
            final int max = annotation.max();
            field.setAccessible(true);
            boolean flag = false;
            final Object value = field.get(obj);

            final String message = "@Size constraint";
            if (field.getType().equals(String.class)) {
                final String s = (String) field.get(obj);
                if (!compare(s.length() , min , max)) {
                    throw new ValidateException(message , field.getName() , s);
                }
                flag = true;
            }
            if (value instanceof Collection) {
                if (!compare(((Collection) value).size() , min , max)) {
                    throw new ValidateException(message , field.getName() , value);
                }
                flag = true;
            }

            if (value instanceof Integer) {
                if (!compare((Integer) value , min , max)) {
                    throw new ValidateException(message , field.getName() , value);
                }
                flag = true;
            }
            if (!flag) {
                throw new IllegalStateException("Illegal Exception was thrown in "
                        + obj.getClass().getName()
                );
            }
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "IllegalAccessException :", e);
        }
    }
}
