package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * -//- (see NotBlankConstraint)
 */
public class PatternConstraint implements Validator {
    @Target(value = { METHOD , FIELD , ANNOTATION_TYPE , CONSTRUCTOR , PARAMETER })
    @Retention(RUNTIME)
    /** */
    public @interface Pattern {
        String regexp();
    }
    private boolean checkWithRegExp(final String regexp , final String userNameString) {
        final java.util.regex.Pattern p = java.util.regex.Pattern.compile(regexp);
        final Matcher m = p.matcher(userNameString);
        return m.matches();
    }
    @Override
    public void validate(final Object obj) throws ValidatorException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(Pattern.class)) {
                f.setAccessible(true);
                new NotBlankConstraint().validate(f , obj);
                String message = "@Pattern in " + obj.getClass().getName();
                try {
                    if (!checkWithRegExp(f.getAnnotation(Pattern.class).regexp() ,
                            (String) f.get(obj))) {
                        throw new ValidatorException(message);
                    }
                } catch (PatternSyntaxException e) {
                    throw new ValidatorException(message);
                } catch (IllegalAccessException e) {
                    System.out.print("IllegalAccessException in " + obj.getClass().getName());
                }
            }
        }
    }
}

