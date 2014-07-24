package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import javax.xml.bind.ValidationException;

/**
 * Created by artem on 19.07.14.
 */
public interface Validator{
    public void validate(Object value) throws ValidatorException, IllegalAccessException, ValidationException;//T is class
}
