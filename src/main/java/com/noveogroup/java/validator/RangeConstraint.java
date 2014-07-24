package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
/**
 * Created by artem on 21.07.14.
 */
public class RangeConstraint implements Validator {

    /**
     * Created by artem on 19.07.14.
     // */


    @Target({FIELD, METHOD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER})
    @Retention(RUNTIME)
    public @interface Range {
            int min();
            int max();
        }
        public void validate(Object obj) throws ValidatorException {
            Field[] fields=obj.getClass().getDeclaredFields();
            for(Field f:fields){
                if(f.isAnnotationPresent(Range.class)) {
                    int min=f.getAnnotation(Range.class).min();
                    int max=f.getAnnotation(Range.class).max();
                    validate(f,obj,min,max);
                }
            }
        }
    public void validate(Field f,Object obj,int min,int max) throws ValidatorException{
        try {
            f.setAccessible(true);
            if(!(f.isEnumConstant())){
                throw new IllegalAccessException();
            }
            if (((Integer) f.get(obj) > max) ||
                    ((Integer) f.get(obj) < min)) {
                throw new ValidatorException("@Range constraint in " + obj.getClass().getName());
            }
        }
        catch(IllegalAccessException e){
            System.out.print("Illegal Exception was thrown in " + obj.getClass().getName());
        }

    }
}
