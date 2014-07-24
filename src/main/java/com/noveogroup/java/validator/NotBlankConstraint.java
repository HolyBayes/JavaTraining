package com.noveogroup.java.validator;

import com.noveogroup.java.validator.SizeConstraint.*;
import org.hibernate.validator.constraints.NotBlank;
import sun.security.validator.ValidatorException;
import com.noveogroup.java.validator.NotNullConstraint.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
/**
 * Created by artem on 22.07.14.
 */
public class NotBlankConstraint implements Validator {
    @Target(value={METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER})
    @Retention(RUNTIME)
    public @interface NotBlank {
    }
    public void validate(Object obj) throws ValidatorException{
        Field[] fields=obj.getClass().getDeclaredFields();
        for(Field f:fields){
            if(f.isAnnotationPresent(NotBlank.class)) {
                f.setAccessible(true);
                new SizeConstraint().validate(f,obj,1,-1);//@Size(min=1)
                new NotNullConstraint().validate(f,obj);//@NotNull
                    validate(f,obj);
            }
        }
    }
    public void validate(Field f,Object obj) throws ValidatorException{
        if (f.getType().getName() != "java.lang.String") {
            throw new ValidatorException("@NotBlank Not a String in" + obj.getClass().getName());
        }
    }
}
