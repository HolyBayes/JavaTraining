package com.noveogroup.java.generator;

import com.noveogroup.java.validator.*;

import java.lang.reflect.Field;
import java.util.*;
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
                 * key-> is a name of class, ->value is count of this class
                 */
                final Class<? extends Object> c = Class.forName(entry.getKey());
                final int value = entry.getValue();
                for (int i = 0; i < value; i++) {
                    final Object obj = genObj(c, valid);
                    result.offer(obj);
                    System.out.print(result.size() + "\n");
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
        if (sizeAnnotation
                && ((value.length() < min)
                || ((value.length() > max)
                && (max != Size.INFINITE)))) {
            result = false;
        }
        if (patternAnnotation
                && !PatternValidator.checkWithRegExp(regexp , value)) {
            result = false;
        }
        if (notNullAnnotation
                && value == null) {
            result = false;
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
        if (notNullAnnotation
            && value == null) {
            result = false;
        }
        if (rangeAnnotation
                && ((value < min)
                    || (value > max))) {
            result = false;
        }
        return result;
    }

    /**
     *
     * @param c is class for generating object's instance
     * @param valid true when you generate valid object
     * @return generated object
     */
    private Object genObj(final Class<? extends Object> c, final boolean valid) {
        Object obj = null;
        try {
            obj = c.newInstance();
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                setField(obj, field, valid);
            }
        } catch (InstantiationException ie) {
            LOG.log(Level.FINE, ie.getMessage(), ie);
        } catch (IllegalAccessException iae) {
            LOG.log(Level.FINE, iae.getMessage(), iae);
        }
        return obj;
    }

    private void setField(final Object obj, final Field field, final boolean valid)
        throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType().equals(String.class)) {
            final boolean sizeAnnotation = checkSize(field);
            final boolean notNullAnnotation = checkNotNull(field);
            final boolean patternAnnotation = checkPattern(field);
            final int min = getMinSize(field);
            final int max = getMaxSize(field);
            final String regexp = getRegexp(field);
            final String fieldValue = genString(sizeAnnotation,
                    notNullAnnotation,
                    patternAnnotation,
                    min,
                    max,
                    regexp,
                    valid);
            field.set(obj , fieldValue);
        }
        if ("int".equals(field.getType().getName())
                || (field.getType().equals(Integer.class))) {
            final boolean rangeAnnotation = checkRange(field);
            final boolean notNullAnnotation = checkNotNull(field);
            final boolean finalAnnotation = checkFinal(field);
            final int min = getRangeVal(field).get(0);
            final int max = getRangeVal(field).get(1);
            if (!finalAnnotation) {
                final Integer fieldValue = genInt(notNullAnnotation,
                        rangeAnnotation,
                        min,
                        max,
                        valid);
                field.set(obj, fieldValue);
            }
        }
    }
    private String genString(final boolean sizeAnnotation,
                             final boolean notNullAnnotation,
                             final boolean patternAnnotation,
                             final int min,
                             final int max,
                             final String regexp,
                             final boolean valid) {
        if (!notNullAnnotation
                && !patternAnnotation
                && !sizeAnnotation) {
            return GEN.nextEmail();
        }
        boolean flag = !valid;
        String fieldValue = null;
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
        return fieldValue;
    }

    /**
     * Check's param field for @Size annotation.
     * @param field
     * @return true if @Size is present.
     */
    private boolean checkSize(final Field field) {
        final boolean result = field.isAnnotationPresent(Size.class)
                || field.isAnnotationPresent(Pattern.class);
        return result;
    }

    /**
     * Returns true if param field has @NotNull.
     * @param field
     * @return true if @NotNull is present
     */
    private boolean checkNotNull(final Field field) {
        final boolean result = field.isAnnotationPresent(NotNull.class)
                || field.isAnnotationPresent(Pattern.class);
        return result;
    }

    /**
     * blablabla.
     * @param field
     * @return
     */
    private boolean checkPattern(final Field field) {
        final boolean patternAnnotation = field.isAnnotationPresent(Pattern.class);
        return patternAnnotation;
    }
    private int getMinSize(final Field field) {
        final List<Integer> mins = new LinkedList<Integer>();
        mins.add(0);
        if(field.isAnnotationPresent(Size.class)) {
            mins.add(field.getAnnotation(Size.class).min());
        }
        if (field.isAnnotationPresent(Pattern.class)) {
            mins.add(1);
        }
        return Collections.max(mins);
    }
    private int getMaxSize(final Field field) {
        int max = Size.INFINITE;
        if (field.isAnnotationPresent(Size.class)) {
            max = field.getAnnotation(Size.class).max();
        }
        return max;
    }
    private String getRegexp(final Field field) {
        if (field.isAnnotationPresent(Pattern.class)) {
            return field.getAnnotation(Pattern.class).regexp();
        }
        return null;
    }
    private boolean checkRange(final Field field) {
        return field.isAnnotationPresent(Range.class);
    }
    private boolean checkFinal(final Field field) {
        return field.isAnnotationPresent(Final.class);
    }

    /**
     * Returns min and max of @Range.
     * @param field
     * @return [0] is min, [1] is max
     */
    private List<Integer> getRangeVal(final Field field) {
        final List<Integer> result = new LinkedList<Integer>();
        int min = 0;
        int max = 0;
        if (field.isAnnotationPresent(Range.class)) {
            min = field.getAnnotation(Range.class).min();
            max = field.getAnnotation(Range.class).max();
        }
        result.add(min);
        result.add(max);
        return result;
    }
    private Integer genInt(final boolean notNullAnnotation,
                           final boolean rangeAnnotation,
                           final int min,
                           final int max,
                           final boolean valid) {

        boolean flag = !valid;
        int fieldValue = GEN.nextInt();

        if (!notNullAnnotation && !rangeAnnotation) {
            flag = valid;
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
        return fieldValue;
    }
}
