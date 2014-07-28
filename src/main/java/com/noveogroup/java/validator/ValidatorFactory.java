package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

/**
 * Factory compares all specialized annotation's validators in
 * one static method validate()
 * @author artem ryzhikov
 */
public abstract class ValidatorFactory {
    public static void validate(final Object obj) throws ValidatorException {
        new NotBlankConstraint().validate(obj);
        new NotNullConstraint().validate(obj);
        new PatternConstraint().validate(obj);
        new RangeConstraint().validate(obj);
        new SizeConstraint().validate(obj);
    }
}
