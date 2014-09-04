package com.noveogroup.java.validator;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * NotBlankConstraint exists @NotBlank annotation (means String field is not empty and not null) and
 * it's validator validate().
 * @author artem ryzhikov
 */
public class NotBlankValidator implements Validator {
    private static final Logger LOG = Logger.getLogger(NotBlankValidator.class.getName());

    private final NotBlank annotation;

    public NotBlankValidator(final Annotation annotation) {
        this.annotation = (NotBlank) annotation;
    }
    @Override
    public void validate(final Object obj , final Field field) throws ValidateException {
        if (!field.getType().equals(String.class)) {
            Object value = new Object();
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                LOG.log(Level.FINE, "IllegalAccessException in Object"
                        + obj.getClass().getName()
                        + "\n in field"
                        + field.getName());
            }
            throw new ValidateException("@NotBlank Not a String in Object " ,
                    field.getName() ,
                    value);
        }
    }
}
