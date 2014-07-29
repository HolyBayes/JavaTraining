package com.noveogroup.java.validator;

import sun.security.validator.ValidatorException;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Vector;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
/**
 * Created by artem on 21.07.14.
 */
public class SizeConstraint implements Validator {

    /**
     * Created by artem on 19.07.14.
     // */


    @Target({FIELD, METHOD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER})
    @Retention(RUNTIME)
    public @interface Size {
        int min() default -1;
        int max() default -1;
    }
    @Override
    public void validate(Object obj) throws ValidatorException{
        Field[] fields=obj.getClass().getDeclaredFields();
        for(Field f:fields){
            if(f.isAnnotationPresent(Size.class)){
                int min=f.getAnnotation(Size.class).min();
                int max=f.getAnnotation(Size.class).max();
            }
        }
    }
    public void validate(Field f,Object obj,int min,int max) throws ValidatorException{
        try{
            f.setAccessible(true);
            boolean flag=false;
            if(f.getType().getName()=="java.lang.String"){
                String s=(String)f.get(obj);
                if (((s.length()>max)
                        &&(max!=-1))
                        ||
                        ((s.length()< min))
                            &&(min!=-1)){
                    throw new ValidatorException("@Size constraint in " + obj.getClass().getName());
                }
                flag=true;
            }
            if(f.isEnumConstant()){
                Vector v=(Vector)f.get(obj);
                if(((v.size()>max)
                        &&(max!=-1))
                        ||
                        ((v.size()< min))
                            &&(min!=-1)){
                    throw new ValidatorException("@Size constraint in " + obj.getClass().getName());
                }
                flag=true;
            }
            if (!flag) {
                throw new IllegalStateException("");
            }
        } catch (IllegalAccessException e) {
            System.out.print("Illegal Exception was thrown in " + obj.getClass().getName());
        }
    }
}
