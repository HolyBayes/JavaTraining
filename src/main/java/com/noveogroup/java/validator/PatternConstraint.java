package com.noveogroup.java.validator;

import org.hibernate.validator.constraints.NotBlank;
import sun.security.validator.ValidatorException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by artem on 22.07.14.
 */
public class PatternConstraint implements Validator {
    @Target(value={METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER})
    @Retention(RUNTIME)
    public @interface Pattern{
        public String regexp();
    }
    private boolean checkWithRegExp(String regexp,String userNameString){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regexp);
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }
    @Override
    public void validate(Object obj) throws ValidatorException{
        Field[] fields=obj.getClass().getDeclaredFields();
        for(Field f:fields) {
            if (f.isAnnotationPresent(Pattern.class)) {
                f.setAccessible(true);
                new NotBlankConstraint().validate(f,obj);//@NotBlank
                try {
                    if (!checkWithRegExp(f.getAnnotation(Pattern.class).regexp(),
                            (String)f.get(obj))) {
                        throw new ValidatorException("@Pattern in "+obj.getClass().getName());
                    }
                }
                catch (PatternSyntaxException e){
                    throw new ValidatorException("@Pattern in "+obj.getClass().getName());
                }
                catch(IllegalAccessException e){
                    System.out.print("IllegalAccessException in " + obj.getClass().getName());
                }
            }
        }
    }
}

