package com.noveogroup.java.validator;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * -//- (see NotBlankConstraint)
 * @author artem ryzhikov
 */

public class RangeValidator implements Validator {
    private static Logger log = Logger.getLogger(RangeValidator.class.getName());
    private static Range annotation;

    public RangeValidator(final Annotation annotation) {
        this.annotation = (Range) annotation;
    }

    @Override
    public void validate(final Object obj , final Field field)
            throws ValidateException {
        try {
            final int min = annotation.min();
            final int max = annotation.max();
            field.setAccessible(true);
            final Object value = field.get(obj);

            if (!(value instanceof Integer)) {
                throw new IllegalAccessException();
            }
            if (((Integer) field.get(obj) > max)
                    ||
                    ((Integer) field.get(obj) < min)) {
                throw new ValidateException("@Range constraint" ,
                        field.getName() ,
                        field.get(obj));
            }
        } catch (IllegalAccessException e) {
            log.log(Level.SEVERE , "IllegalAccessException was thrown :" , e);
        }
    }
}
