package com.noveogroup.java.generator;

import java.lang.reflect.Field;
import java.util.Map;
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
    public Stack<Object> gen(Map<String , Integer> classes) {
        Stack<Object> result = new Stack<Object>();
        try {
            for (Map.Entry<String, Integer> entry : classes.entrySet()) {
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
                            field.set(obj , (String) GEN.nextEmail());
                        }
                    }
                    result.push(obj);
                }
            }
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        } catch (InstantiationException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        } catch (IllegalAccessException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }
}
