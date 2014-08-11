package com.noveogroup.java.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * NotBlankConstraint exists @NotBlank annotation (means String field is not empty and not null) and it's validator
 * validate()
 * @author artem ryzhikov
 */
public class NotBlankConstraint implements Validator {
    private final static Logger LOG = Logger.getLogger(NotBlankConstraint.class.getName());
    @Target(value = { METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER } )
    @Retention(RUNTIME)
    public @interface NotBlank {
    }
    final static SizeConstraint SIZE_CONSTRAINT = new SizeConstraint();
    final static NotNullConstraint NOT_NULL_CONSTRAINT = new NotNullConstraint();
    @Override
    public void validate(final Object obj) throws ValidateException {
        final Field[] fields=obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(NotBlank.class)) {
                f.setAccessible(true);
                //@Size(min=1)
                SIZE_CONSTRAINT.validate(f, obj, 1, SIZE_CONSTRAINT.INFINITE);
                //@NotNull
                NOT_NULL_CONSTRAINT.validate(f, obj);
                //Not a String
                validate(f , obj);
            }
        }
    }
    public void validate(final Field f , final Object obj) throws ValidateException {
        if (!f.getType().equals(java.lang.String.class)) {
            String value = new String();
            try {
                f.setAccessible(true);
                value = f.get(obj).toString();
            } catch (IllegalAccessException e) {
                LOG.log(Level.FINE, "IllegalAccessException in Object" +
                        obj.getClass().getName() +
                        "\n in field" +
                        f.getName());
            }
            throw new ValidateException("@NotBlank Not a String in Object " +
                    obj.getClass().getName() +
                    "\n in Field: " +
                    f.getName() +
                    "\n with value: " +
                    value);
        }
    }
}
