package com.noveogroup.java;

import com.noveogroup.java.generator.PojoFactory;
import com.noveogroup.java.my_concurrency.BlockingQueue;
import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generating -> Serialization -> Deserialization -> Validate.
 * @author artem ryzhikov
 */

class Main {
    private static final Properties PROP = new Properties();
    private static final String PROP_PATH = "src/main/resources/config.properties";
    private static String input;
    private static String output;
    private static String mode;
    private static Integer queueSize = 0;
    private static Integer workerCount = 0;
    private static final int[] COUNTS = {5000, 5, 3000, 7};
    private static final String[] CLASS_NAMES = {"com.noveogroup.java.generator.Pojo1",
        "com.noveogroup.java.generator.Pojo2" };
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        try {
            final FileInputStream is = new FileInputStream(PROP_PATH);
            PROP.load(is);
            input = PROP.getProperty("INPUT");
            output = PROP.getProperty("OUTPUT");
            mode = PROP.getProperty("MODE");
            queueSize = Integer.parseInt(PROP.getProperty("QUEUE_SIZE"));
            workerCount = Integer.parseInt(PROP.getProperty("WORKER_COUNT"));
        } catch (IOException ioe) {
            LOG.log(Level.SEVERE, ioe.getMessage(), ioe);
        }
        final File inputFile = new File(input);
        final File outputFile = new File(output);
        final int intMode = Integer.parseInt(mode);
        final Map<String, Integer> correct = new HashMap<String , Integer>();
        final Map<String, Integer> incorrect = new HashMap<String , Integer>();
        for (int i = 0; i < CLASS_NAMES.length; i++) {
            correct.put(CLASS_NAMES[i], COUNTS[2 * i]);
            incorrect.put(CLASS_NAMES[i], COUNTS[2 * i + 1]);
        }

        final PojoFactory pojoFactory = new PojoFactory();
        final Queue<Object> objects = pojoFactory.gen(correct, true);
        objects.addAll(pojoFactory.gen(incorrect, false));
        final Serializer serializer = new Serializer(inputFile , outputFile);

        try {
            final int limit = objects.size();
            int i = 0;
            while (i < limit) {
                i++;
                serializer.store(objects.poll());
            }
        } catch (IOException e) {
            final String message = "Wrong output";
            LOG.log(Level.SEVERE, message, e);
            LOG.info(message + e.getMessage());

        }
        MainThread.run(intMode,
              serializer,
              queueSize,
              LOG,
              workerCount);
    }
}

/**
 * Created only to avoid big Class Data Abstraction.
 */
class MainThread {
    public static void run(final int intMode,
                           final Serializer serializer,
                           final int queueSize,
                           final Logger log,
                           final int workerCount) {
        final SimpleBlockQueue<Object> queue;
        if (intMode == 0) {
            queue = new BlockingQueue<Object>(queueSize);
        } else {
            queue = new MyBlockingQueue<Object>(queueSize);
        }
        final AtomicInteger valid = new AtomicInteger();
        valid.set(0);
        final AtomicInteger invalid = new AtomicInteger();
        invalid.set(0);
        final AtomicBoolean flag = new AtomicBoolean(false);
        final Reader reader = new Reader(queue , serializer , flag);
        final Worker worker = new Worker(queue , flag, valid, invalid);
        final Thread readThread = new Thread(reader);
        readThread.start();
        final Thread[] workers = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Thread(worker);
            workers[i].start();
            try {
                workers[i].join();
            } catch (InterruptedException ie) {
                log.log(Level.SEVERE, ie.getMessage(), ie);
            }
        }
        log.info("Valid: " + valid.get() + "\nInvalid: " + invalid.get());
    }
}
