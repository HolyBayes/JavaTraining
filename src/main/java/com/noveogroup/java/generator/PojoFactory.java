package com.noveogroup.java.generator;

import com.noveogroup.java.validator.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates POJO.
 * @author artem ryzhikov
 * CORCOUNT - number of correct POJO's, NCORCOUNT - of non-correct
 */
public class PojoFactory {
    private static final Generator GEN = new Generator();
    private static final Logger LOG = Logger.getLogger(PojoFactory.class.getName());


    /**
     * @param objects number of generated POJO's. Map consist of String (class name) like KEY and Integer (class number)
     *                like VALUE
     * @param valid true when you want to generate valid objects
     * @return
     */
    public Queue<Object> gen(final Map<String , Integer> objects, final boolean valid) {
        final Queue<Object> result = new LinkedList<Object>();
        try {
            for (Map.Entry<String, Integer> entry : objects.entrySet()) {
                /**
                 * key is a name of class, value is count of this class
                 */
                final Class<? extends Object> c = Class.forName(entry.getKey());
                final int value = entry.getValue();
                for (int i = 0; i < value; i++) {
                    final Object obj = c.newInstance();
                    final Field[] fields = obj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.getType().equals(String.class)) {
                            String fieldValue = null;
                            boolean sizeAnnotation = false;
                            boolean notNullAnnotation = false;
                            boolean patternAnnotation = false;
                            int min = 0;
                            int max = Size.INFINITE;
                            String regexp = null;
                            final String dateFormat = null;

                            if (field.isAnnotationPresent(Size.class)) {
                                sizeAnnotation = true;
                                min = field.getAnnotation(Size.class).min();
                                max = field.getAnnotation(Size.class).max();
                            }
                            if (field.isAnnotationPresent(NotNull.class)) {
                                notNullAnnotation = true;
                            }
                            if (field.isAnnotationPresent(Pattern.class)) {
                                notNullAnnotation = true;
                                sizeAnnotation = true;
                                min = 1;
                                patternAnnotation = true;
                                regexp = field.getAnnotation(Pattern.class).regexp();
                            }
                            boolean flag = !valid;
                            if (!notNullAnnotation
                                    && !patternAnnotation
                                    && !sizeAnnotation) {
                                flag = valid;
                            }
                            while (flag != valid) {
                                fieldValue = GEN.nextEmail();
                                flag = checkForValid(fieldValue ,
                                        sizeAnnotation ,
                                        notNullAnnotation ,
                                        patternAnnotation ,
                                        min ,
                                        max ,
                                        regexp);

                            }
                            field.set(obj , fieldValue);
                        }
                        if ("int".equals(field.getType().getName())
                                || (field.getType().equals(Integer.class))) {
                            boolean rangeAnnotation = false;
                            boolean notNullAnnotation = false;
                            int min = 0;
                            int max = 0;
                            if (field.isAnnotationPresent(Range.class)) {
                                rangeAnnotation = true;
                                max = field.getAnnotation(Range.class).max();
                                min = field.getAnnotation(Range.class).min();
                            }
                            if (field.isAnnotationPresent(NotNull.class)) {
                                notNullAnnotation = true;
                            }
                            boolean flag = !valid;
                            int fieldValue = GEN.nextInt();

                            if (!notNullAnnotation && !rangeAnnotation) {
                                flag = valid;
                            }
                            if (field.isAnnotationPresent(Final.class)) {
                                flag = valid;
                                try {
                                    fieldValue = (Integer) field.get(obj);
                                } catch (IllegalAccessException iae) {
                                    LOG.log(Level.SEVERE, iae.getMessage(), iae);
                                }
                            }
                            while (flag != valid) {
                                if (valid && rangeAnnotation) {
                                    fieldValue = min + GEN.nextInt(max - min);
                                } else {
                                    fieldValue = GEN.nextInt();
                                }
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
//                    System.out.print(result.size() + "\n");
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
     * @return true if value is valid and false in other cases
     */
    boolean checkForValid(final String value ,
                          final boolean sizeAnnotation ,
                          final boolean notNullAnnotation ,
                          final boolean patternAnnotation ,
                          final int min ,
                          final int max ,
                          final String regexp) {
        boolean result = true;
        if (sizeAnnotation) {
            if ((value.length() < min)
                    || ((value.length() > max) && (max != Size.INFINITE))) {
                result = false;
            }
        }
        if (patternAnnotation) {
            if (!PatternValidator.checkWithRegExp(regexp , value)) {
                result = false;
            }
        }
        if (notNullAnnotation) {
            if (value == null) {
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
        if (notNullAnnotation) {
            if (value == null) {
                result = false;
            }
        }
        if (rangeAnnotation) {
            if ((value < min)
                    || (value > max)) {
                result = false;
            }
        }
        return result;
    }
}
