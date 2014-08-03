package com.noveogroup.java.validator;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory compares all specialized annotation's validators in
 * one static method validate()
 * @author artem ryzhikov
 */
public class ValidatorFactory {
    private static List<Validator> validators = new ArrayList<Validator>();
    private static Logger log = Logger.getLogger(ValidatorFactory.class.getName());
    public ValidatorFactory() {
        validators.add(new NotBlankConstraint());
        validators.add(new NotNullConstraint());
        validators.add(new PatternConstraint());
        validators.add(new RangeConstraint());
        validators.add(new SizeConstraint());
    }
    public static void validate(final Object obj) throws ValidateException {
        try {
            boolean flag = true;
            for (int i = 0; i < validators.size(); i++) {
                try {
                    validators.get(i).validate(obj);
                } catch (ValidateException e){
                    log.log(Level.SEVERE , e.getMessage());
                    flag = false;
                }
            }
            if(!flag) {
                throw new ValidateException(obj.getClass().getName());
            }
        } catch (IllegalAccessException e){
            log.log(Level.SEVERE , e.getMessage());
        }

    }
}
