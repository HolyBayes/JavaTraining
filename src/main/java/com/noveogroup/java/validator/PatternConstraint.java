package com.noveogroup.java.validator;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * -//- (see NotBlankConstraint)
 * @author artem ryzhikov
 */
public class PatternConstraint implements Validator {
    private final static Logger LOG = Logger.getLogger(PatternConstraint.class.getName());
    private final static NotNullConstraint NOT_NULL_CONSTRAINT = new NotNullConstraint();

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
    public void validate(final Object obj) throws ValidateException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f:fields) {
            if (f.isAnnotationPresent(Pattern.class)) {
                f.setAccessible(true);
                NOT_NULL_CONSTRAINT.validate(f, obj);
                String value = new String();
                try {
                    value = f.get(obj).toString();
                } catch (IllegalAccessException e) {
                    LOG.log(Level.FINE, "IllegalAccessException in Object" +
                            obj.getClass().getName() +
                            "\n in field" +
                            f.getName());
                }
                String message = "@Pattern in "  +
                        obj.getClass().getName() +
                        "\n in Field: " +
                        f.getName() +
                        "\n with value: " +
                        value;
                try {
                    if (!checkWithRegExp(f.getAnnotation(Pattern.class).regexp() ,
                            (String) f.get(obj))) {
                        throw new ValidateException(message);
                    }
                } catch (PatternSyntaxException e) {
                    throw new ValidateException(message);
                } catch (IllegalAccessException e) {
                    LOG.log(Level.SEVERE, "IllegalAccessException in RegExp:", e);
                }
            }
        }
    }
}

