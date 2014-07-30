
package com.noveogroup.java;

import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.serialize.Serializer;


import static com.noveogroup.java.generator.Generator.*;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author artem ryzhikov
 */
class Main {
    private final static int QUEUE_SIZE = 1000;
    private static Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        final Stack<Object> stack = generate();
        final File input = new File(args[0]);
        final File output = new File(args[1]);
        final int mode = Integer.parseInt(args[2]);
        final Serializer serializer = new Serializer(input , output);
        try {
            for (int i = 0; i < (CORCOUNT + NCORCOUNT); i++) {
                serializer.store(stack.pop());
            }
        }
        catch (IOException e) {
            String message = "Wrong output";
            log.log(Level.SEVERE , message , e);
            System.out.print(message + e.getMessage());
            log.info(message + e.getMessage());

        }
        //java concurrency frameworks
        final BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(QUEUE_SIZE);
        final MyBlockingQueue<Object> myQueue = new MyBlockingQueue<Object>(QUEUE_SIZE);
        final AtomicBoolean flag = new AtomicBoolean(false);
        final Reader reader = new Reader(queue , myQueue , serializer , flag , mode);
        final Worker worker = new Worker(queue , myQueue , flag , mode);
        new Thread(reader).start();
        new Thread(worker).start();

    }
}