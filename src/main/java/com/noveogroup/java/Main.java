
package com.noveogroup.java;

import com.noveogroup.java.my_concurrency.BlockingQueue;
import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;


import static com.noveogroup.java.generator.Generator.*;

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
    private static Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
    Map<String , Integer> classes = new HashMap<String , Integer>();
    
  /*      args = new String[3];
        args[0] = "temp.out";
        args[1] = "temp.out";
        args[2] = "0";
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
        final SimpleBlockQueue<Object> queue;
        if(mode == 0){
            queue = new BlockingQueue<Object>(QUEUE_SIZE);
        } else{
            queue = new MyBlockingQueue<Object>(QUEUE_SIZE);
        }
        final AtomicBoolean flag = new AtomicBoolean(false);
        final Reader reader = new Reader(queue , serializer , flag);
        final Worker worker = new Worker(queue , flag);
        new Thread(reader).start();
        new Thread(worker).start();
*/
    }
}