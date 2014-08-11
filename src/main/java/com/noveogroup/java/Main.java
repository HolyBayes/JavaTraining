
package com.noveogroup.java;


import com.noveogroup.java.generator.PojoFactory;
import com.noveogroup.java.my_concurrency.BlockingQueue;
import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author artem ryzhikov
 */
class Main {

    public static final int QUEUE_SIZE = 1000;
    public static final int[] COUNTS = {50000};
    public static final String[] CLASS_NAMES = {"com.noveogroup.java.generator.MailMessage"};
    private static Logger LOG = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        final Map<String , Integer> classes = new HashMap<String , Integer>();
        for(int i=0 , limit = CLASS_NAMES.length; i < limit; i++){
            classes.put(CLASS_NAMES[i] , COUNTS[i]);
        }
        final String INPUT = "temp.out";
        final String OUTPUT = "temp.out";
        final String MODE = "0";
        final PojoFactory pojoFactory = new PojoFactory();
        final Stack<Object> stack = pojoFactory.gen(classes);
        final File INPUT_FILE = new File(INPUT);
        final File OUTPUT_FILE = new File(OUTPUT);
        final int INT_MODE = Integer.parseInt(MODE);

        final Serializer SERIALIZER = new Serializer(INPUT_FILE, OUTPUT_FILE);

        try {
            for (int i = 0 , limit = stack.size(); i < limit; i++) {
                SERIALIZER.store(stack.pop());
            }
        } catch (IOException e) {
            String message = "Wrong output";
            LOG.log(Level.SEVERE, message, e);
            System.out.print(message + e.getMessage());
            LOG.info(message + e.getMessage());

        }
        final SimpleBlockQueue<Object> QUEUE;
        if(INT_MODE == 0){
            QUEUE = new BlockingQueue<Object>(QUEUE_SIZE);
        } else{
            QUEUE = new MyBlockingQueue<Object>(QUEUE_SIZE);
        }
        final AtomicBoolean FLAG = new AtomicBoolean(false);
        final Reader READER = new Reader(QUEUE , SERIALIZER , FLAG);
        final Worker WORKER = new Worker(QUEUE , FLAG);
        new Thread(READER).start();
        new Thread(WORKER).start();

}
}