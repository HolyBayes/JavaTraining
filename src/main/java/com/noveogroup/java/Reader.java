package com.noveogroup.java;


import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author artem ryzhikov
 */
class Reader implements Runnable {

    private static Logger log = Logger.getLogger(Reader.class.getName());
    private final SimpleBlockQueue queue;
    private Serializer serializer;
    /**true till Reader works*/
    private AtomicBoolean flag;

    public Reader(
            final SimpleBlockQueue queue ,
            final Serializer serializer ,
            final AtomicBoolean flag) {
        this.queue = queue;
        this.serializer = serializer;
        this.flag = flag;
    }

    @Override
    public void run() {
        this.flag.set(true);
        System.out.println("[Reader] run\n");
        while (this.flag.get()) {
            try {
                final Object obj = this.serializer.load();
                    queue.put(obj);
            } catch (InterruptedException e) {
                final String message = "Interrupted exception in put()";
                System.out.print(message);
                log.log(Level.SEVERE , message , e);
            } catch (ClassNotFoundException e) {
                System.out.print(e.getMessage());
                log.log(Level.SEVERE , "ClassNotFoundException in put()" , e);
            } catch (IOException e) {
                System.out.print("[Reader] finished\n");
                log.info("[Reader] finished");
                this.flag.set(false);
            }
        }
    }


}
