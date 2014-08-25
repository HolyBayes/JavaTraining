package com.noveogroup.java.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by artem on 26.08.14.
 */
public class DateValidator implements Validator {
    private final Date annotation;
    private final Logger log = Logger.getLogger(DateValidator.class.getName());
    public DateValidator(final Annotation annotation) {
        this.annotation = (Date) annotation;
    }

    @Override
    public void validate(final Object obj , final Field field) throws ValidateException {
        String dateToValidate = null;
        try {
            field.setAccessible(true);
            if (this.annotation.DATE_FORMAT == null) {
                throw new ValidateException("date format is null",
                        field.getName(),
                        field.get(obj));
            }
            final String dateFormat = annotation.DATE_FORMAT;
            dateToValidate = (String) field.get(obj);
            final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setLenient(false);
            final java.util.Date date = sdf.parse(dateToValidate);

        } catch (IllegalAccessException iae) {
            log.log(Level.SEVERE, iae.getMessage(), iae);
        } catch (ParseException pe) {
            log.log(Level.SEVERE, pe.getMessage(), pe);
            throw new ValidateException(pe.getMessage(), field.getName(), dateToValidate);
        }

    }
}
