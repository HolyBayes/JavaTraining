package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

/**
 * Created by artem on 23.07.14.
 */
public class ValidatorFactory {
    public static void validate(Object obj) throws ValidatorException{
        new NotBlankConstraint().validate(obj);
        new NotNullConstraint().validate(obj);
        new PatternConstraint().validate(obj);
        new RangeConstraint().validate(obj);
        new SizeConstraint().validate(obj);
    }
}
