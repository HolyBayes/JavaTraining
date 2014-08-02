package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;
;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * NotBlankConstraint exists @NotBlank annotation (means String field is not empty and not null) and it's validator
 * validate()
 * @author artem ryzhikov
 */
public class NotBlankConstraint implements Validator {
    @Target(value = { METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER } )
    @Retention(RUNTIME)
    public @interface NotBlank {
    }
    @Override
    public void validate(final Object obj) throws ValidatorException {
        /** */
        final Field[] fields=obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(NotBlank.class)) {
                f.setAccessible(true);
                //@Size(min=1)
                new SizeConstraint().validate(f , obj , 1 , -1);
                //@NotNull
                new NotNullConstraint().validate(f , obj);
                    validate(f , obj);
            }
        }
    }
    public void validate(final Field f , final Object obj) throws ValidatorException {
        if (!f.getType().equals(java.lang.String.class)) {
            throw new ValidatorException("@NotBlank Not a String in" + obj.getClass().getName());
        }
    }
}
