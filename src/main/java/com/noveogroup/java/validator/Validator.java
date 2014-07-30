package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import javax.xml.bind.ValidationException;

/**
 * Validator interface
 */
public interface Validator {
    public void validate(Object value) throws ValidatorException, IllegalAccessException, ValidationException;
}
