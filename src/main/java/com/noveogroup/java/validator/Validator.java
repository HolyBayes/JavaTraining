package com.noveogroup.java.validator;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Validator interface
 * @author artem ryzhikov
 */
public interface Validator {
    public void validate(final Object object , final Field field) throws ValidateException , IllegalAccessException;
}
