package com.noveogroup.java;
import com.noveogroup.java.my_concurrency.BlockingQueue;
import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created only to avoid big Class Data Abstraction.
 */
public final class MainThread {
    public MainThread() {

    }
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
