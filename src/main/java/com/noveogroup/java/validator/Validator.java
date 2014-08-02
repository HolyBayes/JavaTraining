package com.noveogroup.java.validator;



/**
 * Validator interface
 * @author artem ryzhikov
 */
public interface Validator {
    public void validate(Object value) throws ValidateException, IllegalAccessException;
}
