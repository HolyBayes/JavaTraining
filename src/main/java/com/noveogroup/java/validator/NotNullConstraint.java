package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
/**
 * Created by artem on 19.07.14.
 // */

    public class NotNullConstraint implements Validator{
        @Target(value={METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER})
        @Retention(RUNTIME)
        public @interface NotNull {
        }
        public void validate(Object obj) throws ValidatorException {
            Field[] fields=obj.getClass().getDeclaredFields();
            for(Field f:fields){
                if(f.isAnnotationPresent(NotNull.class)) {
                    validate(f,obj);
                }
            }
        }
        public void validate(Field f,Object obj) throws ValidatorException{
            try {
                f.setAccessible(true);
                if (f.get(obj) == null) {
                    throw new ValidatorException("@NotNull constraint in "+obj.getClass().getName());
                }
            }
            catch(IllegalAccessException e){
                System.out.print("Illegal Exception was thrown in " + obj.getClass().getName());
            }
        }
    }