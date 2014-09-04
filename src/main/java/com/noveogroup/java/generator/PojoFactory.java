package com.noveogroup.java.generator;

import com.noveogroup.java.validator.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates POJO
 * @author artem ryzhikov
 * CORCOUNT - number of correct POJO's, NCORCOUNT - of non-correct
 */
public class PojoFactory {
    private final static Generator GEN = new Generator();
    private final static Logger LOG = Logger.getLogger(PojoFactory.class.getName());


    /**
     *
     * @param objects number of generated POJO's. Map consist of String (class name) like KEY and Integer (class number)
     *                like VALUE
     * @param valid true when you want to generate valid objects
     * @return
     */
    public Queue<Object> gen(final Map<String , Integer> objects , boolean valid) {
        Queue<Object> result = new LinkedList<>();
        try {
            for (Map.Entry<String, Integer> entry : objects.entrySet()) {
                /**
                 * key is a name of class, value is count of this class
                 */
                Class<? extends Object> c = Class.forName(entry.getKey());
                int value = entry.getValue();
                for (int i = 0; i < value; i++){
                    Object obj = c.newInstance();
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for(Field field : fields) {
                        field.setAccessible(true);

                        if(field.getType().equals(String.class)) {
                            String fieldValue = null;
                            boolean sizeAnnotation = false;
                            boolean notNullAnnotation = false;
                            boolean patternAnnotation = false;
                            int min = 0;
                            int max = Size.INFINITE;
                            String regexp = null;
                            if(field.isAnnotationPresent(Size.class)) {
                                sizeAnnotation = true;
                                min = field.getAnnotation(Size.class).min();
                                max = field.getAnnotation(Size.class).max();
                            }
                            if(field.isAnnotationPresent(NotNull.class)) {
                                notNullAnnotation = true;
                            }
                            if(field.isAnnotationPresent(Pattern.class)) {
                                patternAnnotation = true;
                                regexp = field.getAnnotation(Pattern.class).regexp();
                            }
                            boolean flag = !valid;
                            while(flag != valid) {
                                fieldValue = GEN.nextEmail();
                                flag = checkForValid (fieldValue ,
                                        sizeAnnotation ,
                                        notNullAnnotation ,
                                        patternAnnotation ,
                                        min ,
                                        max ,
                                        regexp);

                            }
                            field.set(obj , fieldValue);
                        }
                        if(field.getType().getName().equals("int") ||
                                (field.getType().equals(Integer.class))) {
                            boolean rangeAnnotation = false;
                            boolean notNullAnnotation = false;
                            int min = 0;
                            int max = 0;
                            if(field.isAnnotationPresent(Range.class)) {
                                rangeAnnotation = true;
                                max = field.getAnnotation(Range.class).max();
                                min = field.getAnnotation(Range.class).min();
                            }
                            if(field.isAnnotationPresent(NotNull.class)) {
                                notNullAnnotation = true;
                            }
                            boolean flag = !valid;
                            int fieldValue = 0;
                            while(flag != valid) {
                                fieldValue = GEN.nextInt();
                                flag = checkForValid(fieldValue ,
                                        notNullAnnotation ,
                                        rangeAnnotation ,
                                        min ,
                                        max);
                            }
                            field.set(obj , fieldValue);
                        }
                    }
                    result.offer(obj);
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param value checkable for valid value
     * @param sizeAnnotation true if @Size annotation present
     * @param notNullAnnotation true if @NotNull annotation present
     * @param patternAnnotation true if @Pattern annotation present
     * @param min min of @Size annotation
     * @param max max of @Size annotation
     * @param regexp regular expression of @Pattern annotation
     * @return
     */
    boolean checkForValid(final String value ,
                          final boolean sizeAnnotation ,
                          final boolean notNullAnnotation ,
                          final boolean patternAnnotation ,
                          final int min ,
                          final int max ,
                          final String regexp) {
        boolean result = true;
        if(sizeAnnotation) {
            if((value.length() < min) ||(value.length() > max)) {
                result = false;
            }
        }
        if(patternAnnotation) {
            if(!PatternValidator.checkWithRegExp(regexp , value)) {
                result = false;
            }
        }
        if(notNullAnnotation) {
            if(value == null) {
                result = false;
            }
        }
        return result;
    }

    /**
     *
     * @param value checkable for valid value
     * @param notNullAnnotation true if @NotNull annotation present
     * @param rangeAnnotation true if @Range annotation present
     * @param min min of @Range annotation
     * @param max max of @Range annotation
     * @return true if value is valid and false in other cases
     */
    private boolean checkForValid(final Integer value ,
                                  final boolean notNullAnnotation ,
                                  final boolean rangeAnnotation ,
                                  final int min ,
                                  final int max) {
        boolean result = true;
        if(notNullAnnotation) {
            if(value == null) {
                result = false;
            }
        }
        if(rangeAnnotation) {
            if((value < min) ||
                    (value > max)) {
                result = false;
            }
        }
        return result;
    }
}
