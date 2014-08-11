package com.noveogroup.java.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * NotNullConstraint class consist of -//- (see NotBlankConstraint)
 * @author artem ryzhikov
 */
public class NotNullConstraint implements Validator {
    private final static Logger LOG = Logger.getLogger(NotNullConstraint.class.getName());

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
            String value = new String();
            try{
                value = f.get(obj).toString();
            } catch (IllegalAccessException e) {
                LOG.log(Level.FINE, "IllegalAccessException in Object" +
                        obj.getClass().getName() +
                        "\n in field" +
                        f.getName());
            }
            if (f.get(obj) == null) {
                throw new ValidateException("@NotNull constraint in "  +
                        obj.getClass().getName() +
                        "\n in Field: " +
                        f.getName() +
                        "\n with value: " +
                        value);
            }
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, "IllegalAccessException :", e);
        }
    }
}