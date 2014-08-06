package com.noveogroup.java.generator;

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

    private static Logger log = Logger.getLogger(PojoFactory.class.getName());
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
                    result.push(c.newInstance());
                }
            }
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE , e.getMessage());
        } catch (InstantiationException e) {
            log.log(Level.SEVERE , e.getMessage());
        } catch (IllegalAccessException e) {
            log.log(Level.SEVERE , e.getMessage());
        }
        return result;
    }
}
