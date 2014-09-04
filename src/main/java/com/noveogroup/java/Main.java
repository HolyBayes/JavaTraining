
package com.noveogroup.java;


import com.noveogroup.java.generator.PojoFactory;
import com.noveogroup.java.my_concurrency.BlockingQueue;
import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author artem ryzhikov
 */

class Main {
    final static String INPUT = "temp.out";
    final static String OUTPUT = "temp.out";
    final static String MODE = "0";
    final static int QUEUE_SIZE = 1000;
    final static int BUFFER_SIZE = 1000;
    final static int[] COUNTS = {50000, 5};
    final static String[] CLASS_NAMES = {"com.noveogroup.java.generator.MailMessage"};
    final static Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        final File INPUT_FILE = new File(INPUT);
        final File OUTPUT_FILE = new File(OUTPUT);
        final int INT_MODE = Integer.parseInt(MODE);
        final Map<String, Integer> correct = new HashMap<>();
        final Map<String, Integer> incorrect = new HashMap<>();
        for (int i = 0, limit = CLASS_NAMES.length; i < limit; i++) {
            correct.put(CLASS_NAMES[i], COUNTS[2 * i]);
            incorrect.put(CLASS_NAMES[i], COUNTS[2 * i + 1]);
        }

        final PojoFactory pojoFactory = new PojoFactory();
        final Queue<Object> objects = pojoFactory.gen(correct, true);
        objects.addAll(pojoFactory.gen(incorrect, false));
        final Serializer SERIALIZER = new Serializer(INPUT_FILE, OUTPUT_FILE);

//        try {
        int limit = objects.size();
        int i = 0;
        while (i < limit) {
            // temporary buffer's queue
            Queue<Object> temp = new LinkedList<>();
            for (int j = 0; (j < BUFFER_SIZE) ||
                    (i < limit); j++) {
                temp.offer(objects.poll());
                i++;
            }
        }
//        } catch (IOException e) {
//            String message = "Wrong output";
//            LOG.log(Level.SEVERE, message, e);
//            System.out.print(message + e.getMessage());
//            LOG.info(message + e.getMessage());
//
//        }
        final SimpleBlockQueue<Object> QUEUE;
        if (INT_MODE == 0) {
            QUEUE = new BlockingQueue<Object>(QUEUE_SIZE);
        } else {
            QUEUE = new MyBlockingQueue<Object>(QUEUE_SIZE);
        }
        final AtomicBoolean FLAG = new AtomicBoolean(false);
        final Reader READER = new Reader(QUEUE, SERIALIZER, FLAG);
        final Worker WORKER = new Worker(QUEUE, FLAG);
        new Thread(READER).start();
        new Thread(WORKER).start();

    }
}